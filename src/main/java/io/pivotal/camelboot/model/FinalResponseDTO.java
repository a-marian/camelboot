package io.pivotal.camelboot.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"id", "name", "category"})
public class FinalResponseDTO {

    @JsonProperty(value = "id", required = true)
    private String id;

    @JsonProperty(value = "name" )
    private String name;

    @JsonProperty(value="category")
    private String category;




}
