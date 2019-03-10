package com.cor.cep.util;

import com.cor.cep.event.UserSimple;

import javax.security.sasl.SaslClient;

public class UserSimplePropertiesExtractor {

    public void PropertiesSetter(String str, UserSimple userSimple){


        //String str = "[NetFilter-O]IN= OUT=eth0 SRC=10.64.119.57 DST=151.101.1.69 LEN=246 TOS=0x00 PREC=0x00 TTL=64 ID=48269 DF PROTO=TCP SPT=47612 DPT=443 WINDOW=317 RES=0x00 ACK PSH URGP=0 ";
        if(str.contains("netFilter")) {

            int firstIndexOfSRC = str.indexOf("SRC=");
            int firstIndexOfSpace = str.indexOf(" ", firstIndexOfSRC);
            String SrcPt = str.substring(firstIndexOfSRC, firstIndexOfSpace);
            //remove the SCR=
            SrcPt = SrcPt.replace("SRC=","");
            userSimple.setSrcIp(SrcPt);


            // factory set
            userSimple.setDesIp(    str.substring( str.indexOf("DST="), str.indexOf(" ", str.indexOf("DST=")) ).replace("DST=","")   );

            userSimple.setProto(    str.substring( str.indexOf("PROTO="), str.indexOf(" ", str.indexOf("PROTO=")) ).replace("PROTO=", "")   );

            if(userSimple.getProto().equalsIgnoreCase("TCP")){

                userSimple.setFlag(    str.substring( str.indexOf(" ", str.indexOf("RES=")), str.indexOf("URGP=") )   );

                userSimple.setScrPt(    str.substring( str.indexOf("SPT="), str.indexOf(" ", str.indexOf("SPT=")) ).replace("SPT=","")   );

                userSimple.setDstPt(    str.substring( str.indexOf("DPT="), str.indexOf(" ", str.indexOf("DPT=")) ).replace("DPT=","")   );
            }
            else if(userSimple.getProto().equalsIgnoreCase("ICMP")) {

                userSimple.setType(    str.substring( str.indexOf("TYPE="), str.indexOf(" ", str.indexOf("TYPE=")) ).replace("TYPE=","")   );

                userSimple.setCode(    str.substring( str.indexOf("CODE="), str.indexOf(" ", str.indexOf("CODE=")) ).replace("CODE=","")   );

            }




            //userSimple.setScrPt(    str.substring( str.indexOf("SPT="), str.indexOf(" ", str.indexOf("SPT=")) )   );

            //userSimple.setDstPt(    str.substring( str.indexOf("DPT="), str.indexOf(" ", str.indexOf("DPT=")) )   );

            //userSimple.setFlag(    str.substring( str.indexOf(" ", str.indexOf("RES=")), str.indexOf("URGP=") )   );
        }

    }
}
