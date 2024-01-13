package com.server.server.model.Seller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.server.server.model.ResponseEntity;
import com.server.server.model.Request.ModSeller;
import com.server.server.model.Request.SellerPassword;
import com.server.server.model.Request.SellerProfileRequest;
import com.server.server.model.Request.UserDelete;

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

    @GetMapping("/forgotPassword/{id}/{telNum}")
    public ResponseEntity<String> forgotPassword(@PathVariable String id, @PathVariable String telNum){
        String pass = sellerDao.forgotPassword(id, telNum);
        System.out.println("Richiesta recupero password da: "+id+"  con risultato: "+pass);
        if(pass==null){
            return new ResponseEntity<>("Impossibile procedere al recupero", 400);
        }
        else{
            return new ResponseEntity<String>(pass, "Recupero riuscito", 200);
        }
    }

    @PostMapping("/deleteSeller")
    public ResponseEntity<Void> deleteSeller(@RequestBody UserDelete request){
        if(sellerDao.deleteSeller(request.getUser(), request.getPassword())){
            return new ResponseEntity<>("Utente eliminato", 200);
        }

        return new ResponseEntity<>("Password errata", 400);
    }


    @PostMapping("/changeSellerPassword")
    public ResponseEntity<Void> changePassword(@RequestBody SellerPassword request){
        if(sellerDao.changePassword(request.getId(), request.getOldPassword(), request.getNewPassword()))
            return new ResponseEntity<>("Password modificata", 200);
        
        return new ResponseEntity<>("Errore nella richiesta", 400);
    }

    @PostMapping("/getSellerProfile")
    public ResponseEntity<Seller> getSellerProfile(@RequestBody SellerProfileRequest request){
        
        Seller seller = sellerDao.getSeller(request.getId());
        if(seller!=null){
            if(seller.getPassword().equals(request.getPassword()))
                return new ResponseEntity<Seller>(seller, null, 200);
            else
                return new ResponseEntity<>(null, 400);
        }
        else{
            return new ResponseEntity<>("", 400);
        }
        
    }


    @PostMapping("/modSeller")
    public ResponseEntity<Void> modSeller(@RequestBody ModSeller request){
        if(sellerDao.goodSeller(request.getId(), request.getPassword())){
            if(sellerDao.changeSeller(request)){
                return new ResponseEntity<>("Modifiche salvate", 200);
            }
        }

        return new ResponseEntity<>("Errore nella richiesta", 400);
    }

}
