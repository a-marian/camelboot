package io.pivotal.camelboot.router;


import io.pivotal.camelboot.processor.FirstRequestProcessor;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;

import org.apache.camel.model.rest.RestBindingMode;

import org.apache.camel.util.toolbox.AggregationStrategies;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


import java.util.LinkedList;
import java.util.List;


@Component
public class CamelRouter extends RouteBuilder {

    @Value("${service.host}")
    private String serviceHost;


    @Override
    public void configure() throws Exception {

        FirstRequestProcessor first = new FirstRequestProcessor();

        /*
         * Common rest configuration
         */

        restConfiguration()
                .host(serviceHost)
                .bindingMode(RestBindingMode.json)
                .contextPath("/api")
                .apiContextPath("/doc")
                .apiProperty("api.title", "API-Gateway  REST API")
                .apiProperty("api.description", "Operations that can be invoked in the api-gateway")
                .apiProperty("api.license.name", "Apache License Version 2.0")
                .apiProperty("api.license.url", "http://www.apache.org/licenses/LICENSE-2.0.html")
                .apiProperty("api.version", "1.0.0");


        /*
         * Gateway service
         */

        // full path: /api/gateway
        rest().post("/gateway")
                .description("Invoke all microservices in parallel")
                .outTypeList(String.class)
                .apiDocs(true)
                .responseMessage().code(200).message("OK").endResponseMessage()
                .route()
                .multicast(AggregationStrategies.flexible().accumulateInCollection(LinkedList.class))
                .parallelProcessing()
                .to("direct:aloha")

                .end()
                .transform().body(List.class, list -> list)
                .setHeader("Access-Control-Allow-Credentials", constant("true"))
                .setHeader("Access-Control-Allow-Origin", header("Origin"));


        from("direct:aloha")
                .id("aloha")
                .removeHeaders("accept*")
                .setHeader(Exchange.HTTP_METHOD, constant("POST"))
                .setHeader(Exchange.ACCEPT_CONTENT_TYPE, constant("text/plain"))
                .process(first)
                 .hystrix()
                .hystrixConfiguration().executionTimeoutInMilliseconds(1000).circuitBreakerRequestVolumeThreshold(5).end()
                .id("aloha")
                .groupKey("http://localhost:8090/")
                .to("http4:localhost:8090/snippets/?bridgeEndpoint=true&connectionClose=true")
                .convertBodyTo(String.class)
                .onFallback()
                .transform().constant("Aloha response (fallback)")
                .end();



    }


}
