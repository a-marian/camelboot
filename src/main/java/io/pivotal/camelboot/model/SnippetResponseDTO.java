package io.pivotal.camelboot.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpStatus;


public class SnippetResponseDTO {

    @JsonProperty(value = "user", required = true)
    User user;

    @JsonProperty(value = "status")
    HttpStatus status;

    @JsonProperty(value = "code")
    String code;


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
