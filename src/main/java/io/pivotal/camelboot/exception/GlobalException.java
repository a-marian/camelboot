package io.pivotal.camelboot.exception;

public class GlobalException extends Exception {

    private static final long serialVersionUID = 1L;

    public GlobalException(){}

    public GlobalException(String code){
        super(code);
    }

}
