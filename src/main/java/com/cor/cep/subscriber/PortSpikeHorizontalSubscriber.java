package com.cor.cep.subscriber;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;


@Component
public class PortSpikeHorizontalSubscriber implements StatementSubscriber {

    /** Logger */
    private static Logger LOG = LoggerFactory.getLogger(MonitorEventSubscriber.class);

    /**
     * {@inheritDoc}
     */
    public String getStatement() {

        // Example of simple EPL with a Time Window
        return " select destPt, destIP from VerticalScan.win:length_batch(10) " +
                " group by destPt having count(destPt) >= 2 ";


    }

    /**
     * Listener method called when Esper has detected a pattern match.
     */
    public void update(Map<String, String> eventMap) {

        // count when there are distinct value
        String destPt = (String) eventMap.get("destPt");
        String destIP = (String) eventMap.get("destIP");

        StringBuilder sb = new StringBuilder();
        sb.append("---------------------------------");
        sb.append("\n- [MONITOR] destPt " + destPt);
        sb.append("\n- [MONITOR] destIP " + destIP);
        sb.append("\n---------------------------------");

        LOG.debug(sb.toString());
    }
}