package com.server.server.model.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "user")
public class User {
    
    @Id
    @Column(name="userName")
    private String name;

    @Column(name="password")
    private String password;


    public String getName(){
        return name;
    }

    public String getPassword(){
        return password;
    }

    public void setName(String name){
        this.name=name;
    }

    public void setPassword(String password){
        this.password=password;
    }

    public User(){}

    public User(String userName, String password){
        this.name=userName;
        this.password=password;
    }
}
