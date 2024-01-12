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
            if(seller.getPassword().equals(s.getPassword()) && seller.getName().equals(s.getName()))
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

    public boolean deleteSeller(String id, String password){
        System.out.println("Richiesta delete da: "+id);
        Optional<Seller> s = sellerRepository.findById(id);
        if(s.isPresent()){
            Seller seller = s.get();
            if(seller.getPassword().equals(password)){
                sellerRepository.deleteById(id);
                return true;
            }
            else{
              return false;  
            }
        }
        return false;
    }

    public boolean changePassword(String id, String old, String newPass){
        Optional<Seller> o = sellerRepository.findById(id);
        if(o.isPresent()){
            Seller s = o.get();
            if(s.getPassword().equals(old)){
                s.changePassword(newPass);
                sellerRepository.save(s);
                return true;
            }
            return false;
        }
        else{
            return false;
        }
    }

}
