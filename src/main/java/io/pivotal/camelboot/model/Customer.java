package io.pivotal.camelboot.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * User entity
 *
 */
@ApiModel(description = "Represents an user of the system")
public class Customer {

    @ApiModelProperty(value = "The ID of the user", required = true)
    private Integer id;

    @ApiModelProperty(value = "The name of the user", required = true)
    private String name;

    public Customer() {
    }

    public Customer(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
