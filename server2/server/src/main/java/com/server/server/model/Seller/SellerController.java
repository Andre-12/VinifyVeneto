package com.server.server.model.Seller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.server.server.model.ResponseEntity;

@RestController
public class SellerController {
    
    @Autowired
    private SellerDao sellerDao;

    @PostMapping("/signupSeller")
    public ResponseEntity<Void> addSeller(@RequestBody Seller seller){
        System.out.println("Richiesta di: "+seller.getId()+"   "+seller.getName());
        if(!sellerDao.existSeller(seller)){
            sellerDao.addSeller(seller);
            return new ResponseEntity<>("Registrazione eseguita", 200);
        }
        else{
            return new ResponseEntity<>("Utente già esistente", 200);
        }
    }

    @PostMapping("/loginSeller")
    public ResponseEntity<Seller> loginSeller(@RequestBody Seller seller){
        System.out.println("Richiesta di: "+seller.getId()+"   "+seller.getName()+"  "+seller.getPassword());
        if(sellerDao.loginSeller(seller)){
            return new ResponseEntity<>(seller, "Accesso eseguito", 200);
        }
        else
            return new ResponseEntity<>("Credenziali errate", 400);
    }

    /*@GetMapping("/getSellerInfo/{product}")
    public ResponseEntity<Seller> getSellerInfo(@PathVariable String product){
        Seller s = sellerDao.getSellerInfo(product);
        if(s==null){
            return new ResponseEntity<>("Richiesta fallita", 400);
        }
        else{
            return new ResponseEntity<Seller>(s, "Richiesta eseguita", 200);
        }
    }*/
}
