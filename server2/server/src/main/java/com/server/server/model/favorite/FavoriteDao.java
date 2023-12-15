package com.server.server.model.favorite;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.server.server.model.ResponseEntity;
import com.server.server.model.Request.AddFavoriteRequest;
import com.server.server.model.product.Product;
import com.server.server.model.product.ProductDao;
import com.server.server.model.user.User;
import com.server.server.model.user.UserDao;

@Service
public class FavoriteDao {

    @Autowired
    private UserDao userDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private FavoriteRepository repository;

    public ResponseEntity<Void> addFavorite(AddFavoriteRequest request){
        List<Favorite> l = repository.findAll();
        if(userDao.checkUser(new User(request.getUser(), request.getPassword()))){
            for(Favorite f : l){
                if(f.getUser().equals(request.getUser()) && f.getProduct()==request.getProduct())
                    return new ResponseEntity<>("Prodotto già aggiunto ai preferiti", 400);
            }
            repository.save(new Favorite(request.getUser(), request.getProduct()));
            return new ResponseEntity<>("Aggiunto ai preferiti", 200);
        }
        else{
            return new ResponseEntity<>("Impossibile eseguire la richiesta", 400);
        }
    }

    public List<Product> getFavorites(User user){
        if(userDao.checkUser(user)){
            List<Product> l = new ArrayList<>();
            List<Favorite> f = repository.findAll();
            for(Favorite p : f){
                l.add(productDao.geProduct(p.getProduct()));
            }
            return l;
        }
        else{
            return null;
        }
    }

    public boolean removeFavorite(AddFavoriteRequest request){
        if(userDao.checkUser(new User(request.getUser(), request.getPassword()))){
            List<Favorite> l = repository.findAll();
            for(Favorite f : l){
                if(f.getProduct()==request.getProduct() && f.getUser().equals(request.getUser())){
                    repository.deleteById(f.getId());
                    return true;
                }
            }
            return false;
        }
        else{
            return false;
        }
    }
}
