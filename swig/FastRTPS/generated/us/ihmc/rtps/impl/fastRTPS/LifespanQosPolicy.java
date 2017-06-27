/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.10
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package us.ihmc.rtps.impl.fastRTPS;

public class LifespanQosPolicy extends QosPolicy {
  private transient long swigCPtr;

  protected LifespanQosPolicy(long cPtr, boolean cMemoryOwn) {
    super(FastRTPSJNI.LifespanQosPolicy_SWIGUpcast(cPtr), cMemoryOwn);
    swigCPtr = cPtr;
  }

  protected static long getCPtr(LifespanQosPolicy obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        FastRTPSJNI.delete_LifespanQosPolicy(swigCPtr);
      }
      swigCPtr = 0;
    }
    super.delete();
  }

  public LifespanQosPolicy() {
    this(FastRTPSJNI.new_LifespanQosPolicy(), true);
  }

  public void setDuration(Time_t value) {
    FastRTPSJNI.LifespanQosPolicy_duration_set(swigCPtr, this, Time_t.getCPtr(value), value);
  }

  public Time_t getDuration() {
    long cPtr = FastRTPSJNI.LifespanQosPolicy_duration_get(swigCPtr, this);
    return (cPtr == 0) ? null : new Time_t(cPtr, false);
  }

  public boolean addToCDRMessage(SWIGTYPE_p_CDRMessage_t msg) {
    return FastRTPSJNI.LifespanQosPolicy_addToCDRMessage(swigCPtr, this, SWIGTYPE_p_CDRMessage_t.getCPtr(msg));
  }

}
