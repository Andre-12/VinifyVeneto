package com.server.server.model.Request;


public class AddProductRequest {
    private String sellerId;

    private String sellerPassword;

    private String name;

    private String origin;

    private String type;

    private String description;

    private String price;


    public String getSellerId(){ return this.sellerId;}

    public String getSellerPassword(){ return this.sellerPassword;}

    public String getName(){ return this.name;}

    public String getOrigin(){ return this.origin;}

    public String getType(){ return this.type;}

    public String getDescription(){ return this.description;}

    public String getPrice(){ return this.price;}
}
