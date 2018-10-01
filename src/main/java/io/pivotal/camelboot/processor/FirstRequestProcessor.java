package io.pivotal.camelboot.processor;

import io.pivotal.camelboot.model.RequestHeader;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class FirstRequestProcessor implements Processor {

    private static final Logger LOG = LoggerFactory.getLogger(FirstRequestProcessor.class);

    @Override
    public void process(Exchange exchange) throws Exception {

        int count=0;

        Message input = exchange.getIn();
        Message ouput = exchange.getOut();


        String clientId = input.getBody(String.class);

        LOG.info("Header origin : " + clientId);
        DateFormat format=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String currentDate=format.format(new Date());
        Date creationDate=format.parse(currentDate);

        LOG.debug("Nber of CSV records received by the csv bean : " + count);
    }
}


/**
 * https://developers.redhat.com/blog/2016/11/07/microservices-comparing-diy-with-apache-camel/
 * https://github.com/redhat-helloworld-msa/api-gateway/tree/spring-boot-camel/src/main/java/com/redhat/developers/msa/api_gateway
 *https://github.com/jbossdemocentral/coolstore-microservice/tree/stable-ocp-3.10/coolstore-gw
 * https://microservices.io/patterns/security/access-token.html
 * https://access.redhat.com/documentation/en-us/red_hat_jboss_fuse/6.2/html/apache_camel_component_reference/idu-http4
 * */