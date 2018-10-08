package io.pivotal.camelboot.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RequestBody {

    @JsonProperty(value = "id", required = true)
    String id;

    @JsonProperty(value = "name", required = false)
    String name;

    @JsonProperty(value = "category", required = true)
    String category;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
