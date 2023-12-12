package com.server.server.model.Request;

public class ProductRequest {
    public ProductRequest(String seller, String origin, String type){
        this.seller=seller;
        this.origin=origin;
        this.type=type;
    }

    private String seller;
    private String origin;

    private String type;

    public String getSeller(){
        return this.seller;
    }

    public String getOrigin(){
        return this.origin;
    }

    public String getType(){
        return this.type;
    }
}
