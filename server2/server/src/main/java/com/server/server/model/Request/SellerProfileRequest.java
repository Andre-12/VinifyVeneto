package com.server.server.model.Request;

public class SellerProfileRequest {
    
    private String id;

    private String password;

    public String getId(){
        return this.id;
    }

    public String getPassword(){
        return this.password;
    }

    public SellerProfileRequest(){}
}
