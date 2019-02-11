package com.cor.cep.subscriber;

import com.cor.cep.event.TemperatureEvent;
import com.cor.cep.event.UserSimple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class SynScanEventSubscriber implements StatementSubscriber {

    /**
     * Logger
     */
    private static Logger LOG = LoggerFactory.getLogger(SynScanEventSubscriber.class);

    /**
     * If 2 consecutive temperature events are greater than this - issue a warning
     */
    private static final String WARNING_EVENT_THRESHOLD = "400";


    /**
     * {@inheritDoc}
     */
    public String getStatement() {

        String warningEventExpression1 = "select temp1, temp2 from pattern [ every (temp1 = TemperatureEvent(temperature > 300) -> temp2 = TemperatureEvent(temperature < 1*temp1.temperature) ) ]";
        //return "select avg(temperature) as avg_val from TemperatureEvent.win:time_batch(5 sec)";

        //every at the top is messed up, put it in the first event so that it triggers at every SYN; if put as wrapper of the pattern it will ignore everything
        String SynScanEventExpression = "select EventA, EventB, EventC "
                + "from pattern [ "
                + "              every EventA = UserSimple(proto = 'TCP' and flag = ' SYN ')                "
                + "                 -> EventB = UserSimple(proto = 'TCP' and flag = ' ACK SYN '              "
                + "                                                       and scrPt = EventA.dstPt    		  "
                + "                                                        and dstPt = EventA.scrPt     	   "
                + "                                                         and srcIp = EventA.desIp            "
                + "                                                          and desIp = EventA.srcIp	)        "
                + "                 -> EventC = UserSimple(proto = 'TCP' and flag = ' RST '               	 "
                + "                                                       and scrPt = EventB.dstPt   	      "
                + "                                                        and dstPt = EventB.scrPt     	   "
                + "                                                         and srcIp = EventB.desIp            "
                + "                                                          and desIp = EventB.srcIp	)        "
                + "             ]";

        return SynScanEventExpression;
    }

    /**
     * Listener method called when Esper has detected a pattern match.
     */
    public void update(Map<String, UserSimple> eventMap) {

        // Event A
        UserSimple EventA = (UserSimple) eventMap.get("EventA");
        // Event B
        UserSimple EventB = (UserSimple) eventMap.get("EventB");
        // Event C
        UserSimple EventC = (UserSimple) eventMap.get("EventC");

        StringBuilder sb = new StringBuilder();
        sb.append("--------------------------------------------------");
        sb.append("\n- SYN Scan detected " + EventA + "," + EventB + "," + EventC);
        sb.append("\n- SYN Scan detected " + EventA.getMESSAGE()  );
        sb.append("\n- SYN Scan detected " + EventB.getMESSAGE() );
        sb.append("\n- SYN Scan detected " + EventC.getMESSAGE() );
        sb.append("\n--------------------------------------------------");

        LOG.debug(sb.toString());
    }
}