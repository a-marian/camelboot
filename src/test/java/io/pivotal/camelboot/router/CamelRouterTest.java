package io.pivotal.camelboot.router;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.pivotal.camelboot.model.*;
import org.apache.camel.CamelContext;

import org.apache.camel.builder.RouteBuilder;

import org.apache.camel.test.junit4.CamelTestSupport;

import org.apache.commons.io.IOUtils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.InputStream;



@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(
        locations = "classpath:test.properties")
public class CamelRouterTest extends CamelTestSupport  {

    public static final Logger LOG = LoggerFactory.getLogger(CamelRouterTest.class);

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CamelContext camelContext;




    @Test
    public void shouldSucceed() throws Exception {

        FinalResponseDTO finalResponseDTO = genTestResponse();
        String snippetResponse = generateSnippetResponse();

        String bookServiceResponse = generateBookResponse();

        camelContext.getRouteDefinitions().get(0).adviceWith(camelContext, new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                interceptSendToEndpoint("http:adminhost*")
                        .skipSendToOriginalEndpoint()
                        .to("mock:adminhost").setBody(constant(snippetResponse)) ;

                interceptSendToEndpoint("http:bookhost*")
                        .skipSendToOriginalEndpoint()
                        .to("mock:bookhost").setBody(constant(bookServiceResponse)) ;
            }
        });

        getMockEndpoint("mock:adminhost").expectedMessageCount(1);

        getMockEndpoint("mock:bookhost").expectedMessageCount(1);


        Resource restRequest = new ClassPathResource("SavingBook.json");
        InputStream stream = restRequest.getInputStream();
        String body = IOUtils.toString(stream);
        Gson gson = new GsonBuilder().create();
        FirstRequest firstRequest = gson.fromJson(body, FirstRequest.class);

        LOG.info("Sending request using json payload: {}", body);


        ResponseEntity<FinalResponseDTO> checkoutResponse = restTemplate.postForEntity("/api/gateway",firstRequest, FinalResponseDTO.class);
            FinalResponseDTO finalResponseDTO1 = checkoutResponse.getBody();
       assertNotNull(finalResponseDTO1.getCategory());
       assertNotNull(finalResponseDTO1.getName());

      System.out.print("response "+ finalResponseDTO1.getName() );

    }

    private String generateSnippetResponse(){
        ObjectMapper mapper = new ObjectMapper();

        SnippetResponseDTO snippetResponseDTO = new SnippetResponseDTO();
        snippetResponseDTO.setCode("500");
        snippetResponseDTO.setStatus(org.springframework.http.HttpStatus.OK);
        User user = new User();
        user.setHobby("reading");
        user.setId("1122");
        user.setUserName("lucas");
        user.setName("Lucas");
        user.setLastName("Amstrong");
        user.setPassword("queteimporta");
        snippetResponseDTO.setUser(user);

        String snippetResponseStr="";
        try {
            snippetResponseStr = mapper.writeValueAsString(snippetResponseDTO);
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return snippetResponseStr;
    }

    private String generateBookResponse(){
        ObjectMapper mapper = new ObjectMapper();

        RequestBody requestBody = new RequestBody();
        requestBody.setId("12");
        requestBody.setCategory("fiction");
        requestBody.setName("The stand");

        String requestBodyStr="";
        try{
            requestBodyStr = mapper.writeValueAsString(requestBody);

        }catch (JsonProcessingException e){
            e.printStackTrace();
        }
        return  requestBodyStr;
    }

    private FinalResponseDTO genTestResponse(){
        ObjectMapper mapper = new ObjectMapper();

        FinalResponseDTO finalResponseDTO = new FinalResponseDTO();

        return finalResponseDTO;
    }

}



/**How retrieve original message
 * It works :) exchange.getUnitOfWork().getOriginalInMessage();
 * */