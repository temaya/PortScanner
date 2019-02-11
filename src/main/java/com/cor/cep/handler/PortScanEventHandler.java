package com.cor.cep.handler;

import com.cor.cep.event.UserSimple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.cor.cep.event.TemperatureEvent;
import com.cor.cep.subscriber.StatementSubscriber;
import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;

/**
 * This class handles incoming Temperature Events. It processes them through the EPService, to which
 * it has attached the 3 queries.
 */
@Component
@Scope(value = "singleton")
public class PortScanEventHandler implements InitializingBean{

    /** Logger */
    private static Logger LOG = LoggerFactory.getLogger(PortScanEventHandler.class);

    /** Esper service */
    private EPServiceProvider epService;

    /** customize Scan */
    private EPStatement SynScanEventStatement;

    /** customize Scan */
    @Autowired
    @Qualifier("synScanEventSubscriber")
    private StatementSubscriber synScanEventSubscriber;

    /**
     * Configure Esper Statement(s).
     */
    public void initService() {

        LOG.debug("Initializing Servcie ..");
        Configuration config = new Configuration();
        config.addEventTypeAutoName("com.cor.cep.event");
        epService = EPServiceProviderManager.getDefaultProvider(config);

        //createCriticalTemperatureCheckExpression();
        //createWarningTemperatureCheckExpression();
        //createTemperatureMonitorExpression();
        createSynScanDetectExpression();

    }


    private void createSynScanDetectExpression() {

        LOG.debug("create Syn Scan Monitor");
        SynScanEventStatement = epService.getEPAdministrator().createEPL(synScanEventSubscriber.getStatement());
        SynScanEventStatement.setSubscriber(synScanEventSubscriber);
    }

    /**
     * Handle the incoming PortScanEvent.
     */
    public void handlePortScan(UserSimple event){
        //LOG.debug(event.toString());
        epService.getEPRuntime().sendEvent(event);
    }

    @Override
    public void afterPropertiesSet() {

        LOG.debug("Configuring..");
        initService();
    }
}