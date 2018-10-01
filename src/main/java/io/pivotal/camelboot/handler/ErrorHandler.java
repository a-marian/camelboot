package io.pivotal.camelboot.handler;

import ch.qos.logback.core.pattern.util.RestrictedEscapeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

public class ErrorHandler  implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return isError(response.getStatusCode());
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
            System.out.println("sdfsd");
    }

    private static boolean isError(HttpStatus status){
        HttpStatus.Series series = status.series();
        return(HttpStatus.Series.CLIENT_ERROR.equals(series)
              || HttpStatus.Series.SERVER_ERROR.equals(series));
    }

}
