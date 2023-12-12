package com.server.server.model.product;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "product")
public class Product {
    
    @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public Product(String name, String origin, String type, float price, String descr, String seller){
        this.name=name;
        this.origin=origin;
        this.description=descr;
        this.type=type;
        this.price=price;
        this.seller=seller;
    }

    public Product(){}

    public void setAll(Product newProduct){
        this.name=newProduct.name;
        this.origin=newProduct.origin;
        this.price=newProduct.price;
        this.description=newProduct.description;
        this.type=newProduct.type;
        
    }

    public void setSeller(String s){
        this.seller=s;
    }

    /*public String getSeller(){
        return this.seller;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "seller_id")
    private String seller;*/

}
