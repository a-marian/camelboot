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
