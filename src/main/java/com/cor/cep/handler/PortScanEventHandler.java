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

    private EPStatement AckScanEventStatement;

    private EPStatement UdpScanEventStatement;

    private EPStatement SynScanEClosedPortventStatement;


    private EPStatement monitorEventStatement;

    private EPStatement horizontalEventStatement;

    private EPStatement portSpikeHorizontalEventStatement;



    /** customize Scan */
    @Autowired
    @Qualifier("synScanEventSubscriber")
    private StatementSubscriber synScanEventSubscriber;

    @Autowired
    @Qualifier("synScanClosedPortEventSubscriber")
    private StatementSubscriber SynScanClosedPortEventSubscriber;

    @Autowired
    @Qualifier("ackScanEventSubscriber")
    private StatementSubscriber ackScanEventSubscriber;

    @Autowired
    @Qualifier("udpScanEventSubscriber")
    private StatementSubscriber udpScanEventSubscriber;

    @Autowired
    @Qualifier("closedPortCounterSubscriber")
    private StatementSubscriber monitorEventSubscriber;

    @Autowired
    @Qualifier("horizontalScanCounterSubscriber")
    private StatementSubscriber horizontalScanEventSubscriber;

    @Autowired
    @Qualifier("portSpikeHorizontalSubscriber")
    private StatementSubscriber portSpikeHorizontalSubscriber;


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
        createAckScanDetectExpression();
        createUdpScanDetectExpression();
        createSynScanClosedPortDetectExpression();
        createCounterExpression();
        createVerticalScanCounterExpression();
        createHorizontalSpike();

        //createTable();


    }

    private void createHorizontalSpike() {

        LOG.debug("create Horizontal Port Count Monitor");
        portSpikeHorizontalEventStatement = epService.getEPAdministrator().createEPL(portSpikeHorizontalSubscriber.getStatement());
        portSpikeHorizontalEventStatement.setSubscriber(portSpikeHorizontalSubscriber);
    }

    private void createVerticalScanCounterExpression() {

        LOG.debug("create Horizontal Monitor");
        horizontalEventStatement = epService.getEPAdministrator().createEPL(horizontalScanEventSubscriber.getStatement());
        horizontalEventStatement.setSubscriber(horizontalScanEventSubscriber);
    }

    private void createCounterExpression() {

        LOG.debug("create Timed Average Monitor");
        monitorEventStatement = epService.getEPAdministrator().createEPL(monitorEventSubscriber.getStatement());
        monitorEventStatement.setSubscriber(monitorEventSubscriber);
    }


    private void createSynScanClosedPortDetectExpression() {

        LOG.debug("create Syn Scan ClosedPort Monitor");
        SynScanEClosedPortventStatement = epService.getEPAdministrator().createEPL(SynScanClosedPortEventSubscriber.getStatement());
        SynScanEClosedPortventStatement.setSubscriber(SynScanClosedPortEventSubscriber);
    }

    private void createSynScanDetectExpression() {

        LOG.debug("create Syn Scan Monitor");
        SynScanEventStatement = epService.getEPAdministrator().createEPL(synScanEventSubscriber.getStatement());
        SynScanEventStatement.setSubscriber(synScanEventSubscriber);
    }


    private void createAckScanDetectExpression() {

        LOG.debug("create Ack Scan Monitor");
        AckScanEventStatement = epService.getEPAdministrator().createEPL(ackScanEventSubscriber.getStatement());
        AckScanEventStatement.setSubscriber(ackScanEventSubscriber);
    }

    private void createTable() {

        LOG.debug("create Table");
        String createSchemaClosedPort = "create table closed_port1 (srcIP string, destIP string, srcPt string, destPt string) ";
        epService.getEPAdministrator().createEPL(createSchemaClosedPort);
    }

    private void createUdpScanDetectExpression() {
        LOG.debug("create UDP Scan Monitor");
        UdpScanEventStatement = epService.getEPAdministrator().createEPL(udpScanEventSubscriber.getStatement());
        UdpScanEventStatement.setSubscriber(udpScanEventSubscriber);
    }


    /**
     * Handle the incoming PortScanEvent.
     */
    public void handlePortScan(UserSimple event){
        //LOG.debug(event.toString());
        epService.getEPRuntime().sendEvent(event);

        /*
        if (event.getProto().equalsIgnoreCase( "ICMP")
                && event.getType().equalsIgnoreCase( "8")
                && event.getCode().equalsIgnoreCase( "0")
                && UdpScanEventStatement.isStarted()){
            createSynScanDetectExpression();
            createAckScanDetectExpression();

            //createUdpScanDetectExpression();


        }
        */

    }

    @Override
    public void afterPropertiesSet() {

        LOG.debug("Configuring..");
        initService();
    }
}
