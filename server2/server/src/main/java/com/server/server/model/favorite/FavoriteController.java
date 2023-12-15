package com.server.server.model.favorite;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.server.server.model.ResponseEntity;
import com.server.server.model.Request.AddFavoriteRequest;
import com.server.server.model.user.User;
import com.server.server.model.product.Product;;

@RestController
public class FavoriteController {
    
    @Autowired
    private FavoriteDao favoriteDao;

    @PostMapping("/addFavorite")
    public ResponseEntity<Void> addFavorite(@RequestBody AddFavoriteRequest request){

        return favoriteDao.addFavorite(request);
    }

    @PostMapping("/getFavorites")
    public ResponseEntity<List<Product>> getFavorites(@RequestBody User user){
        System.out.println("Richiesta preferiti da "+user.getName());
        List<Product> l = favoriteDao.getFavorites(user);
        if(l==null){
            return new ResponseEntity<>("Errore nella richiesta", 400);
        }
        else{
            return new ResponseEntity<List<Product>>(l, null, 200);
        }
    }

    @PostMapping("/removeFavorite")
    public ResponseEntity<Void> removeFavorite(@RequestBody AddFavoriteRequest request){
        if(favoriteDao.removeFavorite(request)){
            return new ResponseEntity<>("Prodotto rimosso dai preferiti", 200);
        }
        else{
            return new ResponseEntity<>("Errore nella riciesta", 400);
        }
    }
}
