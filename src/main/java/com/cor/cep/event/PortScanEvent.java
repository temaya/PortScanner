package com.cor.cep.event;

public class PortScanEvent {
    private String Flag;
    private String Proto;
    private String ScrIp;
    private String DestIp;
    private String Spt; // Source Port
    private String Dpt; // Destination Port


    public PortScanEvent(String flag, String proto, String scrIp, String destIp, String spt, String dpt) {
        Flag = flag;
        Proto = proto;
        ScrIp = scrIp;
        DestIp = destIp;
        Spt = spt;
        Dpt = dpt;
    }

    public String getFlag() {
        return Flag;
    }

    public String getProto() {
        return Proto;
    }

    public String getScrIp() {
        return ScrIp;
    }

    public String getDestIp() {
        return DestIp;
    }

    public String getSpt() {
        return Spt;
    }

    public String getDpt() {
        return Dpt;
    }
}
