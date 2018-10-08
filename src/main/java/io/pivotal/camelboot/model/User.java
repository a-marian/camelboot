package io.pivotal.camelboot.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


@JsonPropertyOrder({"id","user_name","user_password", "name", "last_name"})
public class User {


 @JsonProperty()
 private String id;

 @JsonProperty(value = "user_name")
 private String userName;

 @JsonProperty(value = "user_password", required = true)
 private String password;

 private String name;

 @JsonProperty(value = "last_name")
 private String lastName;

    @JsonProperty(value="hobby")
    private String hobby;


 public User(){}

    public User(String userName, String password, String name, String lastName){
        this.id = java.util.UUID.randomUUID().toString();
        this.userName = userName;
        this.password = password;
        this.name = name;
        this.lastName = lastName;
        this.hobby = hobby;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    @Override
    public String toString() {
        return String.format( "userId: " + this.id +
            " userName: " + this.userName +
                        " name" + this.name +
                " lastName:" + lastName +
                " hobby: " + this.hobby
                );
    }
}
