package io.pivotal.camelboot.router;


import io.pivotal.camelboot.exception.GlobalException;
import io.pivotal.camelboot.model.FinalResponseDTO;
import io.pivotal.camelboot.model.FirstRequest;
import io.pivotal.camelboot.model.RequestBody;
import io.pivotal.camelboot.model.SnippetResponseDTO;
import io.pivotal.camelboot.processor.FirstRequestProcessor;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;

import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.rest.RestBindingMode;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;



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
                .consumes(MediaType.APPLICATION_JSON_VALUE).produces(MediaType.APPLICATION_JSON_VALUE)
                .type(FirstRequest.class).outType(FinalResponseDTO.class)
                .route()
                .bean(FirstRequestProcessor.class, "firtsprocess")
                .to("http:adminhost?bridgeEndpoint=false")
                .log("regresando de consulta de hobby")
                .setHeader("CamelJacksonUnmarshalType", simple(SnippetResponseDTO.class.getName()))
                .unmarshal().json(JsonLibrary.Jackson, SnippetResponseDTO.class)

                .bean(FirstRequestProcessor.class, "secondprocess")
                .choice()

                    .when(header("hobby").isEqualTo("reading"))
                        .routeId("bookSaving")
                            .log("Starting to save book")
                            .setHeader(Exchange.HTTP_URI, constant("http://{{ADMIN_SERVICE_HOST}}:{{ADMIN_SERVICE_PORT}}/books"))
                            .convertBodyTo(FirstRequest.class)
                            .marshal().json(JsonLibrary.Jackson, FirstRequest.class)
                            .to("http:bookhost?bridgeEndpoint=false")
                            .setHeader("CamelJacksonUnmarshalType", simple(RequestBody.class.getName()))
                            .unmarshal().json(JsonLibrary.Jackson, RequestBody.class)
                            .log("regresando de guardar libro")


                    .when(header("hobby").isEqualTo("watching"))
                        .routeId("filmsSaving")
                            .log("Starting to save film")
                            .setHeader(Exchange.HTTP_URI, constant("http://{{ADMIN_SERVICE_HOST}}:{{ADMIN_SERVICE_PORT}}/films") )
                            .convertBodyTo(FirstRequest.class)
                            .marshal().json(JsonLibrary.Jackson, FirstRequest.class)
                            .to("http:filmhost?bridgeEndpoint=false")
                            .setHeader("CamelJacksonUnmarshalType", simple(RequestBody.class.getName()))
                            .unmarshal().json(JsonLibrary.Jackson, RequestBody.class)
                            .log("Film saved {body}")


                .endRest();







    }


}
