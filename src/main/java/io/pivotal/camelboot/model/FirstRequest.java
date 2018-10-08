package io.pivotal.camelboot.model;

public class FirstRequest {


     RequestHeader header;


     RequestBody body;


    public RequestHeader getHeader() {
        return header;
    }

    public void setHeader(RequestHeader header) {
        this.header = header;
    }

    public RequestBody getBody() {
        return body;
    }

    public void setBody(RequestBody body) {
        this.body = body;
    }
}
