package io.pivotal.camelboot.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


/**
 * User entity
 *
 */
@ApiModel(description = "Represents an header of Request")
public class RequestHeader {


    @ApiModelProperty(value = "Channel", required = true)
    private String channel;

    @ApiModelProperty(value = "Terminal", required = true)
    private String terminal;

    @ApiModelProperty(value = "Client ID", required = true)
    private String clienteId;

    public  RequestHeader() {
    }

    public RequestHeader(String channel, String terminal, String clienteId){
        this.channel = channel;
        this.terminal = terminal;
        this.clienteId = clienteId;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getTerminal() {
        return terminal;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }

    public String getClienteId() {
        return clienteId;
    }

    public void setClienteId(String clienteId) {
        this.clienteId = clienteId;
    }
}
