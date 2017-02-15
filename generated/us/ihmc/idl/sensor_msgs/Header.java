package us.ihmc.idl.sensor_msgs;
import java.util.ArrayList;
import us.ihmc.idl.IDLSequence;
import us.ihmc.idl.CDR;
import us.ihmc.idl.IDLStruct;

public class Header implements IDLStruct
{
    public Header()
    {
                stamp_ = new us.ihmc.idl.sensor_msgs.Time();        
                frame_id_ = new StringBuilder(255);
                
        
        
    }

        public void setStamp(us.ihmc.idl.sensor_msgs.Time stamp)
        {
            stamp_ = stamp;
        }

        public us.ihmc.idl.sensor_msgs.Time getStamp()
        {
            return stamp_;
        }

        
        public void setFrame_id(StringBuilder frame_id)
        {
            frame_id_ = frame_id;
        }

        public StringBuilder getFrame_id()
        {
            return frame_id_;
        }

        


	static int getMaxCdrSerializedSize()
	{
		return getMaxCdrSerializedSize(0);
	}

	static int getMaxCdrSerializedSize(int current_alignment)
	{
	    int initial_alignment = current_alignment;
	            
	    current_alignment += us.ihmc.idl.sensor_msgs.Time.getMaxCdrSerializedSize(current_alignment);
	    current_alignment += 4 + CDR.alignment(current_alignment, 4) + 255 + 1;

	
	    return current_alignment - initial_alignment;
	}


	static int getCdrSerializedSize(Header data)
	{
		return getCdrSerializedSize(data, 0);
	}

	static int getCdrSerializedSize(Header data, int current_alignment)
	{
	    int initial_alignment = current_alignment;
	            
	    current_alignment += us.ihmc.idl.sensor_msgs.Time.getCdrSerializedSize(data.getStamp(), current_alignment);
	    current_alignment += 4 + CDR.alignment(current_alignment, 4) + data.getFrame_id().length() + 1;

	
	    return current_alignment - initial_alignment;
	}
	
	public void serialize(CDR cdr)
	{


	    cdr.write_type_a(stamp_);

	    if(frame_id_.length() <= 255)
	    cdr.write_type_d(frame_id_);else
	        throw new RuntimeException("frame_id field exceeds the maximum length");
	}
	
	public void deserialize(CDR cdr)
	{

	    	cdr.read_type_a(stamp_);	

	    	cdr.read_type_d(frame_id_);	
	}

    @Override
    public boolean equals(Object other)
    {
        if(other == null) return false;
        if(other == this) return true;
        if(!(other instanceof Header)) return false;
        Header otherMyClass = (Header)other;
        boolean returnedValue = true;

        returnedValue &= this.stamp_.equals(otherMyClass.stamp_);
                
        returnedValue &= this.frame_id_.equals(otherMyClass.frame_id_);
                

        return returnedValue;
    }

    private us.ihmc.idl.sensor_msgs.Time stamp_; 
    private StringBuilder frame_id_; 

}