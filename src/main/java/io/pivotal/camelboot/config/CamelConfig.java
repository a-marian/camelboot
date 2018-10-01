package io.pivotal.camelboot.config;

import org.apache.camel.component.hystrix.metrics.servlet.HystrixEventStreamServlet;
import org.apache.camel.component.servlet.CamelHttpTransportServlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CamelConfig {

    private static final Logger LOG = LoggerFactory.getLogger(CamelConfig.class);

    /**
     * Bind the Camel servlet at the "/api" context path.
     */
    @Bean
    public ServletRegistrationBean camelServletRegistrationBean() {
        ServletRegistrationBean mapping = new ServletRegistrationBean();
        mapping.setServlet(new CamelHttpTransportServlet());
        mapping.addUrlMappings("/api/*");
        mapping.setName("CamelServlet");
        mapping.setLoadOnStartup(1);

        return mapping;
    }

    /**
     * Bind the Hystrix servlet to /hystrix.stream
     */
    @Bean
    public ServletRegistrationBean hystrixServletRegistrationBean() {
        ServletRegistrationBean mapping = new ServletRegistrationBean();
        mapping.setServlet(new HystrixEventStreamServlet());
        mapping.addUrlMappings("/hystrix.stream");
        mapping.setName("HystrixEventStreamServlet");

        return mapping;
    }


}
