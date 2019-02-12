package com.cor.cep.subscriber;


import com.cor.cep.event.UserSimple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class UdpScanEventSubscriber implements StatementSubscriber{

    private static Logger LOG = LoggerFactory.getLogger(UdpScanEventSubscriber.class);


    /**
     * {@inheritDoc}
     */
    public String getStatement() {

        //every at the top is messed up, put it in the first event so that it triggers at every SYN; if put as wrapper of the pattern it will ignore everything
        String UdpScanEventExpression = "select EventA, EventB "
                + "from pattern [ "
                + "              every EventA = UserSimple(proto = 'UDP')                "
                + "                 -> EventB = UserSimple(proto = 'ICMP'                "
                + "                                        and srcIp = EventA.desIp       "
                + "                                        and desIp = EventA.srcIp	)      "
                + "             ]";

        return UdpScanEventExpression;
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
        sb.append("\n- UDP Scan detected " + EventA + "," + EventB);
        sb.append("\n- UDP Scan detected " + EventA.getMESSAGE()  );
        sb.append("\n- UDP Scan detected " + EventB.getMESSAGE() );
        sb.append("\n--------------------------------------------------");

        LOG.debug(sb.toString());
    }
}
