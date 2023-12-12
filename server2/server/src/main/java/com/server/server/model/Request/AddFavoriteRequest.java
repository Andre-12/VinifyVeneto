package com.server.server.model.Request;

public class AddFavoriteRequest {
    private String user;

    private String password;

    private int product;

    public String getUser(){
        return user;
    }

    public String getPassword(){
        return password;
    }

    public int getProduct(){
        return product;
    }
}
