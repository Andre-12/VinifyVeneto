package com.server.server.model.Seller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.server.server.model.product.Product;
import com.server.server.model.product.ProductDao;

@Service
public class SellerDao {
    
    @Autowired
    private SellerRepository sellerRepository;

    public void addSeller(Seller seller){
        if(!existSeller(seller))
            sellerRepository.save(seller);
        
    }

    public boolean existSeller(Seller seller){
        return sellerRepository.findById(seller.getId()).isPresent();
    }

    public boolean goodSeller(String id, String password){
        Optional<Seller> o = sellerRepository.findById(id);

        return o.isPresent() && o.get().getPassword().equals(password);

    }

    public Seller getSeller(String id){
        return sellerRepository.findById(id).get();
    }

    public boolean loginSeller(Seller seller){
        if(existSeller(seller)){
            Seller s = sellerRepository.findById(seller.getId()).get();
            if(seller.getPassword().equals(s.getPassword()))
                return true;
            else
                return false;
        }
        else{
            return false;
        }
    }

    public String forgotPassword(String id, String telNum){
        if(sellerRepository.findById(id).isPresent()){
            Seller s = sellerRepository.findById(id).get();
            if(s.getTelNum().equals(telNum))
                return s.getPassword();
            else
                return null;
        }
        else{
            return null;
        }
    }

}
