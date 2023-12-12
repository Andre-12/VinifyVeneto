package com.server.server.model.Seller;

import java.util.List;

//import org.hibernate.mapping.List;
import com.server.server.model.product.Product;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "seller")
public class Seller {
    
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "password")
    private String password;

    private String name;

    private String prov;

    private String address;

    @Column(name = "telNum")
    private String telNum;

    public String getId(){
        return this.id;
    }

    public String getPassword(){
        return this.password;
    }

    public String getName(){
        return this.name;
    }

    public String getProv(){
        return this.prov;
    }

    public String getAddress(){
        return this.address;
    }

    public String getTelNum(){
        return this.telNum;
    }

    /*@OneToMany(mappedBy = "seller", cascade = CascadeType.ALL)
    private List<Product> products;*/
}
