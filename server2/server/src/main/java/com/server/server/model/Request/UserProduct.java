package com.server.server.model.Request;

import com.server.server.model.product.Product;

public class UserProduct {
    private int id;

    private String name;

    private String origin;

    private String type;

    private float price;

    private String description;

    private String sellerName;

    public String getName(){
        return this.name;
    }

    public String getOrigin(){
        return this.origin;
    }

    public String getType(){
        return this.type;
    }

    public String getDescription(){
        return this.description;
    }

    public int getId(){
        return this.id;
    }

    public String getSellerName(){
        return this.sellerName;
    }

    public float getPrice(){
        return this.price;
    }

}
