package com.cor.cep.subscriber;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ClosedPortCounterSubscriber implements StatementSubscriber {

    /** Logger */
    private static Logger LOG = LoggerFactory.getLogger(MonitorEventSubscriber.class);

    /**
     * {@inheritDoc}
     */
    public String getStatement() {

        // Example of simple EPL with a Time Window
        return "select count(distinct destPt) as val from closed_portSyn.win:length(30) "
                + "group by destIP, srcIP ";
    }

    /**
     * Listener method called when Esper has detected a pattern match.
     */
    public void update(Map<String, Long> eventMap) {

        // count when there are distinct value
        Long val = (Long) eventMap.get("val");

        StringBuilder sb = new StringBuilder();
        sb.append("---------------------------------");
        sb.append("\n- [MONITOR] COUNT " + val);
        sb.append("\n---------------------------------");

        LOG.debug(sb.toString());
    }
}
