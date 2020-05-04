/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.12
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package us.ihmc.rtps.impl.fastRTPS;

public class LocatorList_t {
  private transient long swigCPtr;
  protected transient boolean swigCMemOwn;

  protected LocatorList_t(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(LocatorList_t obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        FastRTPSJNI.delete_LocatorList_t(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public LocatorList_t() {
    this(FastRTPSJNI.new_LocatorList_t__SWIG_0(), true);
  }

  public LocatorList_t(LocatorList_t list) {
    this(FastRTPSJNI.new_LocatorList_t__SWIG_1(LocatorList_t.getCPtr(list), list), true);
  }

  public long size() {
    return FastRTPSJNI.LocatorList_t_size(swigCPtr, this);
  }

  public LocatorList_t assign(LocatorList_t list) {
    return new LocatorList_t(FastRTPSJNI.LocatorList_t_assign(swigCPtr, this, LocatorList_t.getCPtr(list), list), false);
  }

  public void clear() {
    FastRTPSJNI.LocatorList_t_clear(swigCPtr, this);
  }

  public void reserve(long num) {
    FastRTPSJNI.LocatorList_t_reserve(swigCPtr, this, num);
  }

  public void resize(long num) {
    FastRTPSJNI.LocatorList_t_resize(swigCPtr, this, num);
  }

  public void push_back(Locator_t loc) {
    FastRTPSJNI.LocatorList_t_push_back__SWIG_0(swigCPtr, this, Locator_t.getCPtr(loc), loc);
  }

  public void push_back(LocatorList_t locList) {
    FastRTPSJNI.LocatorList_t_push_back__SWIG_1(swigCPtr, this, LocatorList_t.getCPtr(locList), locList);
  }

  public boolean empty() {
    return FastRTPSJNI.LocatorList_t_empty(swigCPtr, this);
  }

  public void erase(Locator_t loc) {
    FastRTPSJNI.LocatorList_t_erase(swigCPtr, this, Locator_t.getCPtr(loc), loc);
  }

  public boolean contains(Locator_t loc) {
    return FastRTPSJNI.LocatorList_t_contains(swigCPtr, this, Locator_t.getCPtr(loc), loc);
  }

  public boolean isValid() {
    return FastRTPSJNI.LocatorList_t_isValid(swigCPtr, this);
  }

  public void swap(LocatorList_t locatorList) {
    FastRTPSJNI.LocatorList_t_swap(swigCPtr, this, LocatorList_t.getCPtr(locatorList), locatorList);
  }

}
