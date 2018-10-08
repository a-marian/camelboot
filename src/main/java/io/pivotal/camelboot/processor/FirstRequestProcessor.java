package io.pivotal.camelboot.processor;

import io.pivotal.camelboot.model.FirstRequest;
import io.pivotal.camelboot.model.SnippetResponseDTO;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.apache.camel.builder.Builder.constant;

public class FirstRequestProcessor implements Processor {

    private static final Logger LOG = LoggerFactory.getLogger(FirstRequestProcessor.class);

    //in memory data store
    public static Map<String, FirstRequest> dataStore = new HashMap<String, FirstRequest>();

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


    public void firtsprocess(Exchange exchange) throws Exception {

        FirstRequest firstRequest = (FirstRequest) exchange.getIn().getBody();
        String userId = firstRequest.getHeader().getUserId();

        saveFirstRequest(userId,firstRequest);
        LOG.info("Header origin : " + userId);
        exchange.getOut().setBody("");
        exchange.getOut().setHeader(Exchange.CONTENT_TYPE, constant("application/json"));
        exchange.getOut().setHeader(Exchange.HTTP_METHOD, HttpMethod.GET);
        exchange.getOut().setHeader(Exchange.HTTP_URI, "http://{{ADMIN_SERVICE_HOST}}:{{ADMIN_SERVICE_PORT}}/users/" + userId);
    }

    public void secondprocess(Exchange exchange) throws Exception {

        SnippetResponseDTO secondRequest = (SnippetResponseDTO) exchange.getIn().getBody();
        String userId = secondRequest.getUser().getId();
        String hobby = secondRequest.getUser().getHobby();
        mergeInMessage(userId, exchange );
        exchange.getOut().setHeader("hobby" , hobby);
        exchange.getOut().setHeader(Exchange.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        exchange.getOut().setHeader(Exchange.HTTP_METHOD, HttpMethod.POST);

    }

    private void saveFirstRequest(String userId, FirstRequest request){
        dataStore.put(userId, request );
    }

    private void mergeInMessage(String userId, Exchange exchange){
        FirstRequest firstRequest =  dataStore.get(userId);
        exchange.getOut().setBody(firstRequest);
        dataStore.remove(userId);

    }
}


/**
 * https://developers.redhat.com/blog/2016/11/07/microservices-comparing-diy-with-apache-camel/
 * https://github.com/redhat-helloworld-msa/api-gateway/tree/spring-boot-camel/src/main/java/com/redhat/developers/msa/api_gateway
 *https://github.com/jbossdemocentral/coolstore-microservice/tree/stable-ocp-3.10/coolstore-gw
 * https://microservices.io/patterns/security/access-token.html
 * https://access.redhat.com/documentation/en-us/red_hat_jboss_fuse/6.2/html/apache_camel_component_reference/idu-http4
 * */