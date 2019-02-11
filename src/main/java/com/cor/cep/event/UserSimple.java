package com.cor.cep.event;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserSimple {

    @SerializedName("__CURSOR")
    @Expose
    private String cURSOR;
    @SerializedName("__REALTIME_TIMESTAMP")
    @Expose
    private String rEALTIMETIMESTAMP;
    @SerializedName("__MONOTONIC_TIMESTAMP")
    @Expose
    private String mONOTONICTIMESTAMP;
    @SerializedName("_BOOT_ID")
    @Expose
    private String bOOTID;
    @SerializedName("_MACHINE_ID")
    @Expose
    private String mACHINEID;
    @SerializedName("_HOSTNAME")
    @Expose
    private String hOSTNAME;
    @SerializedName("_TRANSPORT")
    @Expose
    private String tRANSPORT;
    @SerializedName("PRIORITY")
    @Expose
    private String pRIORITY;
    @SerializedName("SYSLOG_IDENTIFIER")
    @Expose
    private String sYSLOGIDENTIFIER;
    @SerializedName("_SOURCE_MONOTONIC_TIMESTAMP")
    @Expose
    private String sOURCEMONOTONICTIMESTAMP;
    @SerializedName("MESSAGE")
    @Expose
    private String mESSAGE;

    private String SrcIp;
    private String DesIp;
    private String ScrPt;
    private String DstPt;
    private String Proto;
    private String Flag;

    public UserSimple(String mESSAGE, String srcIp, String desIp, String scrPt, String dstPt, String proto, String flag) {
        this.mESSAGE = mESSAGE;
        SrcIp = srcIp;
        DesIp = desIp;
        ScrPt = scrPt;
        DstPt = dstPt;
        Proto = proto;
        Flag = flag;
    }

    public String getSrcIp() {
        return SrcIp;
    }

    public void setSrcIp(String srcIp) {
        SrcIp = srcIp;
    }

    public String getDesIp() {
        return DesIp;
    }

    public void setDesIp(String desIp) {
        DesIp = desIp;
    }

    public String getScrPt() {
        return ScrPt;
    }

    public void setScrPt(String scrPt) {
        ScrPt = scrPt;
    }

    public String getDstPt() {
        return DstPt;
    }

    public void setDstPt(String dstPt) {
        DstPt = dstPt;
    }

    public String getProto() {
        return Proto;
    }

    public void setProto(String proto) {
        Proto = proto;
    }

    public String getFlag() {
        return Flag;
    }

    public void setFlag(String flag) {
        Flag = flag;
    }

    public String getCURSOR() {
        return cURSOR;
    }

    public void setCURSOR(String cURSOR) {
        this.cURSOR = cURSOR;
    }

    public String getREALTIMETIMESTAMP() {
        return rEALTIMETIMESTAMP;
    }

    public void setREALTIMETIMESTAMP(String rEALTIMETIMESTAMP) {
        this.rEALTIMETIMESTAMP = rEALTIMETIMESTAMP;
    }

    public String getMONOTONICTIMESTAMP() {
        return mONOTONICTIMESTAMP;
    }

    public void setMONOTONICTIMESTAMP(String mONOTONICTIMESTAMP) {
        this.mONOTONICTIMESTAMP = mONOTONICTIMESTAMP;
    }

    public String getBOOTID() {
        return bOOTID;
    }

    public void setBOOTID(String bOOTID) {
        this.bOOTID = bOOTID;
    }

    public String getMACHINEID() {
        return mACHINEID;
    }

    public void setMACHINEID(String mACHINEID) {
        this.mACHINEID = mACHINEID;
    }

    public String getHOSTNAME() {
        return hOSTNAME;
    }

    public void setHOSTNAME(String hOSTNAME) {
        this.hOSTNAME = hOSTNAME;
    }

    public String getTRANSPORT() {
        return tRANSPORT;
    }

    public void setTRANSPORT(String tRANSPORT) {
        this.tRANSPORT = tRANSPORT;
    }

    public String getPRIORITY() {
        return pRIORITY;
    }

    public void setPRIORITY(String pRIORITY) {
        this.pRIORITY = pRIORITY;
    }

    public String getSYSLOGIDENTIFIER() {
        return sYSLOGIDENTIFIER;
    }

    public void setSYSLOGIDENTIFIER(String sYSLOGIDENTIFIER) {
        this.sYSLOGIDENTIFIER = sYSLOGIDENTIFIER;
    }

    public String getSOURCEMONOTONICTIMESTAMP() {
        return sOURCEMONOTONICTIMESTAMP;
    }

    public void setSOURCEMONOTONICTIMESTAMP(String sOURCEMONOTONICTIMESTAMP) {
        this.sOURCEMONOTONICTIMESTAMP = sOURCEMONOTONICTIMESTAMP;
    }

    public String getMESSAGE() {
        return mESSAGE;
    }

    public void setMESSAGE(String mESSAGE) {
        this.mESSAGE = mESSAGE;
    }

}
