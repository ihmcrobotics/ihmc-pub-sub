/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.12
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package us.ihmc.rtps.impl.fastRTPS;

public class OwnershipStrengthQosPolicy extends QosPolicy {
  private transient long swigCPtr;

  protected OwnershipStrengthQosPolicy(long cPtr, boolean cMemoryOwn) {
    super(FastRTPSJNI.OwnershipStrengthQosPolicy_SWIGUpcast(cPtr), cMemoryOwn);
    swigCPtr = cPtr;
  }

  protected static long getCPtr(OwnershipStrengthQosPolicy obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        FastRTPSJNI.delete_OwnershipStrengthQosPolicy(swigCPtr);
      }
      swigCPtr = 0;
    }
    super.delete();
  }

  public OwnershipStrengthQosPolicy() {
    this(FastRTPSJNI.new_OwnershipStrengthQosPolicy(), true);
  }

  public boolean addToCDRMessage(SWIGTYPE_p_eprosima__fastrtps__rtps__CDRMessage_t msg) {
    return FastRTPSJNI.OwnershipStrengthQosPolicy_addToCDRMessage(swigCPtr, this, SWIGTYPE_p_eprosima__fastrtps__rtps__CDRMessage_t.getCPtr(msg));
  }

  public void setValue(long value) {
    FastRTPSJNI.OwnershipStrengthQosPolicy_value_set(swigCPtr, this, value);
  }

  public long getValue() {
    return FastRTPSJNI.OwnershipStrengthQosPolicy_value_get(swigCPtr, this);
  }

}
