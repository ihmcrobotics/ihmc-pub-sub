/**
 * Copyright 2017 Florida Institute for Human and Machine Cognition (IHMC)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package us.ihmc.idl.generator;

import java.awt.FileDialog;
import java.awt.Frame;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.anarres.cpp.CppReader;
import org.anarres.cpp.Feature;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;

import com.eprosima.idl.generator.manager.TemplateGroup;
import com.eprosima.idl.generator.manager.TemplateManager;
import com.eprosima.idl.parser.grammar.IDLLexer;
import com.eprosima.idl.parser.grammar.IDLParser;
import com.eprosima.idl.parser.tree.AnnotationDeclaration;
import com.eprosima.idl.parser.tree.AnnotationMember;
import com.eprosima.idl.parser.typecode.PrimitiveTypeCode;
import com.eprosima.idl.parser.typecode.TypeCode;
import com.eprosima.idl.util.Util;

/**
 * The IDL file parser and code generator. 
 * 
 * Includes are resolved against the path of the source .idl and the current directory.
 * 
 * @author Jesper Smith
 *
 */
public class IDLGenerator
{

   public static void main(String[] args) throws IOException
   {
      ArrayList<File> defaultIncludePath = new ArrayList<>();
      defaultIncludePath.add(new File("."));
      
      if (args.length == 3)
      {
         execute(new File(args[0]), args[1], new File(args[2]), defaultIncludePath);
      }
      else
      {
         FileDialog dialog = new FileDialog((Frame) null, "Select idl file", FileDialog.LOAD);
         dialog.setFile("*.idl");
         dialog.setVisible(true);
         if (dialog.getFile() == null)
         {
            dialog.dispose();
            return;
         }
         File file = new File(dialog.getDirectory(), dialog.getFile());

         String res = JOptionPane.showInputDialog("Desired package path");

         if (res == null)
         {
            JOptionPane.showMessageDialog(null, "No package path given");
            dialog.dispose();
            return;
         }
         JFileChooser fileChooser = new JFileChooser();
         fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
         fileChooser.setCurrentDirectory(new File("."));
         if (!(fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION))
         {
            dialog.dispose();
            return;
         }

         execute(file, res, fileChooser.getSelectedFile(), defaultIncludePath);
         dialog.dispose();
      }
   }

   private static Reader createPreProcessedInputStream(File idlFile, List<File> includePathIn) throws IOException
   {

      PreprocessorFilter preprocessor = new PreprocessorFilter();
      preprocessor.addFeature(Feature.KEEPALLCOMMENTS);
      preprocessor.addFeature(Feature.KEEPCOMMENTS);
      preprocessor.addFeature(Feature.LINEMARKERS);
      preprocessor.addFeature(Feature.INCLUDENEXT);

      ArrayList<String> includePath = new ArrayList<>();
      includePath.add(idlFile.getParent());
      for(File include : includePathIn)
      {
         includePath.add(include.getAbsolutePath());
      }

      preprocessor.setSystemIncludePath(includePath);
      preprocessor.setQuoteIncludePath(includePath);
      
      preprocessor.addInput(idlFile);

      
      CppReader reader = new CppReader(preprocessor);
      return new BufferedReader(reader);

   }

   /**
    * Generate java classes from an IDL file
    * 
    * @param idlFilename IDL file to parse
    * @param packageName Target package (IDL Module gets added to this)
    * @param targetDirectory Directory to save the generated files in. The whole package structure is generated in this directory
    * 
    * @throws IOException
    */
   public static void execute(File idlFile, String packageName, File targetDirectory, List<File> includePath) throws IOException
   {
      String idlFilename = idlFile.getAbsolutePath();

      Field field;
      try
      {
         field = TemplateManager.class.getDeclaredField("m_loaderDirectories");
         field.setAccessible(true);
         field.set(null, "us/ihmc/idl/templates");
      }
      catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e)
      {
         throw new RuntimeException("API changed, fixme", e);
      }

      String onlyFileName = Util.getIDLFileNameOnly(idlFilename);

      MyContext ctx = new MyContext(onlyFileName, idlFilename, new ArrayList<>());
      ctx.setPackage(packageName);
      TypeCode.javapackage = ctx.isIsPackageEmpty() ? "" : (ctx.getPackage() + ".");

      // Create default @Key annotation.
      AnnotationDeclaration keyann = ctx.createAnnotationDeclaration("Key", null);
      keyann.addMember(new AnnotationMember("value", new PrimitiveTypeCode(TypeCode.KIND_BOOLEAN), "true"));

      // Create default @Topic annotation.
      AnnotationDeclaration topicann = ctx.createAnnotationDeclaration("Topic", null);
      topicann.addMember(new AnnotationMember("value", new PrimitiveTypeCode(TypeCode.KIND_BOOLEAN), "true"));

      
      AnnotationDeclaration abstractann = ctx.createAnnotationDeclaration("Abstract", null);
      abstractann.addMember(new AnnotationMember("type", new PrimitiveTypeCode(TypeCode.KIND_STRING), "java.lang.Object"));
      abstractann.addMember(new AnnotationMember("impl", new PrimitiveTypeCode(TypeCode.KIND_STRING), ""));
      
      AnnotationDeclaration typecode = ctx.createAnnotationDeclaration("TypeCode", null);
      typecode.addMember(new AnnotationMember("type", new PrimitiveTypeCode(TypeCode.KIND_BOOLEAN), "INVALID_TYPE_CODE"));
      
      // Create template manager
      TemplateManager tmanager = new TemplateManager("FastCdrCommon:Common");

      // Create main template
      TemplateGroup maintemplates = tmanager.createTemplateGroup("main");
      maintemplates.setAttribute("ctx", ctx);

      if(idlFile.exists())
      {
         Reader reader = createPreProcessedInputStream(idlFile, includePath);
         ANTLRInputStream input = new ANTLRInputStream(reader);
         IDLLexer lexer = new IDLLexer(input);
         lexer.setContext(ctx);
         CommonTokenStream tokens = new CommonTokenStream(lexer);
         IDLParser parser = new IDLParser(tokens);
         // Pass the filename without the extension

         parser.specification(ctx, tmanager, maintemplates);

         File packageDir = new File(targetDirectory, ctx.getPackageDir());
         if (packageDir.isDirectory() || packageDir.mkdirs())
         {
            TypesGenerator gen = new TypesGenerator(tmanager, true);
            if(!gen.generate(ctx, packageDir.getPath() + "/", ctx.getPackage(), null))
            {
               throw new IOException("Cannot create Java files");
            }
         }
         else
         {
            throw new IOException("Cannot create output dir " + packageDir);
         }
      }
      else
      {
         throw new IOException("The File " + idlFilename + " was not found.");
      }

   }

}