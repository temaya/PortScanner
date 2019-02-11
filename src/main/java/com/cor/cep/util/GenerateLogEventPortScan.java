package com.cor.cep.util;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.cor.cep.event.UserSimple;
import com.cor.cep.handler.PortScanEventHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonStreamParser;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cor.cep.handler.TemperatureEventHandler;

import javax.jws.soap.SOAPBinding;

/**
 * Just a simple class to create a number of Random TemperatureEvents and pass them off to the
 * TemperatureEventHandler.
 */
@Component
public class GenerateLogEventPortScan {

    /** Logger */
    private static Logger LOG = LoggerFactory.getLogger(GenerateLogEventPortScan.class);

    @Autowired
    private PortScanEventHandler portScanEventHandler;

    private UserSimplePropertiesExtractor SimplePropertiesExtractor = new UserSimplePropertiesExtractor();


    public void startSendingPortScanEventsReadings(final long noOfPortScanEvents) {
        //

        /*
        String content= "";

        try {
            content = new String(Files.readAllBytes(Paths.get("C:\\Users\\min.tran\\IdeaProjects\\esper-demo-nuclear\\src\\main\\resources\\Log.txt")), "UTF-8");
            System.out.println(content);
        } catch (IOException e) {
            e.printStackTrace();
        }


        List<UserSimple> userObjects = new ArrayList<UserSimple>();

        Gson gson = new GsonBuilder().create();
        JsonStreamParser p = new JsonStreamParser(content);


        while (p.hasNext()) {
            JsonElement e = p.next();
            if (e.isJsonObject()) {
                //Map m = gson.fromJson(e, Map.class);
                UserSimple userObject = gson.fromJson(e, UserSimple.class);
                ////Test SimplePropertiesExtractor

                SimplePropertiesExtractor.PropertiesSetter(userObject.getMESSAGE(), userObject);

                System.out.println(userObject.getMESSAGE());
                System.out.println("And the Src Ip is "+ userObject.getSrcIp());
                System.out.println("And the Dsc Ip is "+ userObject.getDesIp());
                System.out.println("And the Source Port is "+ userObject.getScrPt());
                System.out.println("And the Destination Port is "+ userObject.getDstPt());
                System.out.println("And the Protocol is "+ userObject.getProto());
                System.out.println("And the Flag is "+ userObject.getFlag());
                userObjects.add(userObject);



            }
            //// handle other JSON data structures
        }

        System.out.println(userObjects.size());

        */



        //UserSimple userObject = gson.fromJson(userJson, UserSimple.class);
        //LOG.debug(userObject.getMESSAGE());




        ExecutorService xrayExecutor = Executors.newSingleThreadExecutor();

        xrayExecutor.submit(new Runnable() {
            public void run() {
                System.out.println("test");
                String content= "";

                try {
                    content = new String(Files.readAllBytes(Paths.get("C:\\Users\\min.tran\\IdeaProjects\\esper-demo-nuclear\\src\\main\\resources\\Log.txt")), "UTF-8");
             //       System.out.println(content);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                List<UserSimple> userObjects = new ArrayList<UserSimple>();

                Gson gson = new GsonBuilder().create();
                JsonStreamParser p = new JsonStreamParser(content);


                LOG.debug(getStartingMessage());


                int count = 0;
                while (count < noOfPortScanEvents && p.hasNext()) {

                    JsonElement e = p.next();
                    if (e.isJsonObject()) {
                        //Map m = gson.fromJson(e, Map.class);
                        UserSimple userObject = gson.fromJson(e, UserSimple.class);
                        ////Test SimplePropertiesExtractor

                        SimplePropertiesExtractor.PropertiesSetter(userObject.getMESSAGE(), userObject);

                        System.out.println(userObject.getMESSAGE());
                        System.out.println(userObject);
                        System.out.println("And the Flag is *****"+ userObject.getFlag()+"******");
                        System.out.println("And the Src Ip is *****"+ userObject.getSrcIp()+"*****" );
                        System.out.println("And the Dsc Ip is *****"+ userObject.getDesIp()+"*****");
                        System.out.println("And the Source Port is *****"+ userObject.getScrPt()+"*****");
                        System.out.println("And the Destination Port is *****"+ userObject.getDstPt()+"*****");
                        System.out.println("And the Protocol is *****"+ userObject.getProto()+"******");
                        //System.out.println("And the Flag is "+ userObject.getFlag());
                        userObjects.add(userObject);

                        portScanEventHandler.handlePortScan(userObject);
                        //temperatureEventHandler.handlePortScan(userObject);

                    }

                    count++;


                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException exception) {
                        LOG.error("Thread Interrupted", exception);
                    }
                }
                for(int i = 0; i < userObjects.size(); ++i)
                {
                    System.out.println(userObjects.get(i).getProto());
                }

            }
        });
    }

    private String getStartingMessage(){
        StringBuilder sb = new StringBuilder();
        sb.append("\n\n************************************************************");
        sb.append("\n* STARTING - ");
        sb.append("\n* PLEASE WAIT");
        sb.append("\n* A WHILE TO SEE WARNING AND CRITICAL EVENTS!");
        sb.append("\n************************************************************\n");
        return sb.toString();
    }
}
