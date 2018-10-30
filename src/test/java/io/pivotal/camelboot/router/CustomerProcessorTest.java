package io.pivotal.camelboot.router;





import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.pivotal.camelboot.model.FirstRequest;
import io.pivotal.camelboot.model.SnippetResponseDTO;
import io.pivotal.camelboot.model.User;
import io.pivotal.camelboot.processor.CustomerProcessor;
import org.apache.camel.*;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.DefaultExchange;

import org.apache.camel.test.junit4.CamelTestSupport;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.boot.SpringBootConfiguration;

import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;

import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.web.client.RestTemplate;

import org.springframework.web.util.DefaultUriTemplateHandler;
import org.springframework.web.util.UriTemplateHandler;

import java.io.IOException;
import java.io.InputStream;

import static org.springframework.http.HttpStatus.*;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;


@RunWith(SpringRunner.class)
@TestPropertySource(locations="classpath:test.properties")
@RestClientTest(CustomerProcessor.class)
public class CustomerProcessorTest  {


    @Value(value="${snippet.url}")
    private String snippetUrl;

    @Autowired
    private MockRestServiceServer mockServer;


    @Autowired
    CustomerProcessor customerProcessor;


    @Before
     public void setUp() {
        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
        RestTemplate restTemplate = restTemplateBuilder.build();
       this.mockServer = MockRestServiceServer.createServer(restTemplate);
    }


    @Test
    public void testEnrollmentCreateClient() throws IOException, Exception {
        Resource restRequest = new ClassPathResource("SavingBook.json");
        InputStream stream = restRequest.getInputStream();
        String body = IOUtils.toString(stream);
        Gson gson = new GsonBuilder().create();
        FirstRequest firstRequest = gson.fromJson(body, FirstRequest.class);
        String userId = firstRequest.getHeader().getUserId();

        CamelContext ctx = new DefaultCamelContext();
        Exchange exchange = new DefaultExchange(ctx);

        exchange.getIn().setBody(firstRequest);

        UriTemplateHandler uriTemplateHandler = new DefaultUriTemplateHandler();
        String uriExpanded = uriTemplateHandler.expand(snippetUrl, userId).toString();

        mockServer.expect(requestTo(snippetUrl+userId))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.GET))

                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andRespond(withStatus(HttpStatus.OK)
                    .body(generarCustomerResponse())
                    .contentType(MediaType.APPLICATION_JSON));


        customerProcessor.getCustomerInfo(exchange);
        //pasar por rest template el request mockeando

       mockServer.verify();



    }



    private String generarCustomerResponse(){
        ObjectMapper objectMapper = new ObjectMapper();
        String customerResponseStr="";

        SnippetResponseDTO snippetResponseDTO = new SnippetResponseDTO();
         User user = new User();
            user.setId("1231");
            user.setName("Rolando");
            user.setLastName("Gillenhall");

        snippetResponseDTO.setUser(user);
        snippetResponseDTO.setStatus(OK);
        snippetResponseDTO.setCode("200");

        try{
            customerResponseStr = objectMapper.writeValueAsString(snippetResponseDTO);
        }catch (JsonProcessingException e){
            e.printStackTrace();
        }

        return customerResponseStr;
    }

    /**
     * https://www.youtube.com/watch?v=zTsBBBhqfDc
     * */
}