package com.cor.cep.subscriber;

import com.cor.cep.event.UserSimple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class SynScanClosedPortEventSubscriber implements StatementSubscriber{
    /**
     * Logger
     */
    private static Logger LOG = LoggerFactory.getLogger(AckScanEventSubscriber.class);


    /**
     * {@inheritDoc}
     */
    public String getStatement() {

        String warningEventExpression1 = "select temp1, temp2 from pattern [ every (temp1 = TemperatureEvent(temperature > 300) -> temp2 = TemperatureEvent(temperature < 1*temp1.temperature) ) ]";
        //return "select avg(temperature) as avg_val from TemperatureEvent.win:time_batch(5 sec)";

        //every at the top is messed up, put it in the first event so that it triggers at every SYN; if put as wrapper of the pattern it will ignore everything
        String AckScanEventExpression =
                  "insert into closed_portSyn (A, B, C, srcIP, destIP,string, destPt) "
                + "select EventA, EventB, EventB, EventA.srcIp as srcIP, EventA.desIp as destIP, EventA.scrPt as srcPt, EventA.dstPt as destPt "
                + "from pattern [ "
                + "              every EventA = UserSimple(proto = 'TCP' and flag = ' SYN ')                "
                + "                 -> EventB = UserSimple(proto = 'TCP' and flag = ' ACK RST '              "
                + "                                                       and scrPt = EventA.dstPt    		  "
                + "                                                        and dstPt = EventA.scrPt     	   "
                + "                                                         and srcIp = EventA.desIp            "
                + "                                                          and desIp = EventA.srcIp	)        "
                + "             ]";

        return AckScanEventExpression;
    }

    /**
     * Listener method called when Esper has detected a pattern match.
     */
    public void update(Map<String, UserSimple> eventMap) {

        // Event A
        UserSimple EventA = (UserSimple) eventMap.get("EventA");
        // Event B
        UserSimple EventB = (UserSimple) eventMap.get("EventB");


        StringBuilder sb = new StringBuilder();
        sb.append("--------------------------------------------------");
        sb.append("\n- SYN Scan detected " + EventA + "," + EventB);
        sb.append("\n- SYN Scan detected " + EventA.getMESSAGE()  );
        sb.append("\n- ACK Scan detected " + EventB.getMESSAGE() );
        sb.append("\n--------------------------------------------------");

        LOG.debug(sb.toString());
    }
}
