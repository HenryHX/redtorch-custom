/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 4.0.0
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package xyz.redtorch.gateway.ctp.x64v6v3v16v.api;

public class CThostFtdcStrikeOffsetField {
  private transient long swigCPtr;
  protected transient boolean swigCMemOwn;

  protected CThostFtdcStrikeOffsetField(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(CThostFtdcStrikeOffsetField obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  @SuppressWarnings("deprecation")
  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        jctpv6v3v16x64apiJNI.delete_CThostFtdcStrikeOffsetField(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void setInstrumentID(String value) {
    jctpv6v3v16x64apiJNI.CThostFtdcStrikeOffsetField_InstrumentID_set(swigCPtr, this, value);
  }

  public String getInstrumentID() {
    return jctpv6v3v16x64apiJNI.CThostFtdcStrikeOffsetField_InstrumentID_get(swigCPtr, this);
  }

  public void setInvestorRange(char value) {
    jctpv6v3v16x64apiJNI.CThostFtdcStrikeOffsetField_InvestorRange_set(swigCPtr, this, value);
  }

  public char getInvestorRange() {
    return jctpv6v3v16x64apiJNI.CThostFtdcStrikeOffsetField_InvestorRange_get(swigCPtr, this);
  }

  public void setBrokerID(String value) {
    jctpv6v3v16x64apiJNI.CThostFtdcStrikeOffsetField_BrokerID_set(swigCPtr, this, value);
  }

  public String getBrokerID() {
    return jctpv6v3v16x64apiJNI.CThostFtdcStrikeOffsetField_BrokerID_get(swigCPtr, this);
  }

  public void setInvestorID(String value) {
    jctpv6v3v16x64apiJNI.CThostFtdcStrikeOffsetField_InvestorID_set(swigCPtr, this, value);
  }

  public String getInvestorID() {
    return jctpv6v3v16x64apiJNI.CThostFtdcStrikeOffsetField_InvestorID_get(swigCPtr, this);
  }

  public void setOffset(double value) {
    jctpv6v3v16x64apiJNI.CThostFtdcStrikeOffsetField_Offset_set(swigCPtr, this, value);
  }

  public double getOffset() {
    return jctpv6v3v16x64apiJNI.CThostFtdcStrikeOffsetField_Offset_get(swigCPtr, this);
  }

  public void setOffsetType(char value) {
    jctpv6v3v16x64apiJNI.CThostFtdcStrikeOffsetField_OffsetType_set(swigCPtr, this, value);
  }

  public char getOffsetType() {
    return jctpv6v3v16x64apiJNI.CThostFtdcStrikeOffsetField_OffsetType_get(swigCPtr, this);
  }

  public CThostFtdcStrikeOffsetField() {
    this(jctpv6v3v16x64apiJNI.new_CThostFtdcStrikeOffsetField(), true);
  }

}
