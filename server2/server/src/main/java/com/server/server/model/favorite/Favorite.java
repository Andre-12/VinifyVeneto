package com.server.server.model.favorite;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "favorite")
public class Favorite {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String user;

    private int product;

    public String getUser(){
        return this.user;
    }

    public int getProduct(){
        return this.product;
    }

    public int getId(){
        return this.id;
    }

    public Favorite(){}

    public Favorite(String user, int product){
        this.user=user;
        this.product=product;
    }
}
