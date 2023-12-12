package com.example.vinifyveneto.model;

public class Product {
    private int id;

    private String name;

    private String origin;

    private String type;

    private float price;

    private String description;

    private String seller;

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

    public String getSeller(){
        return this.seller;
    }

    public float getPrice(){
        return this.price;
    }
}
