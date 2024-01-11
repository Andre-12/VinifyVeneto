package com.server.server.model.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.server.server.model.ResponseEntity;
import com.server.server.model.Request.UserDelete;

@RestController
public class UserController {
    
    @Autowired
    private UserDao userDao;

    @PostMapping("/signup")
    public ResponseEntity<Void> addUser(@RequestBody User user){
        System.out.println("Richiesta signup da: "+user.getName()+"   "+user.getPassword());
        if(!userDao.checkUser(user)){
            userDao.addUser(user);
            return new ResponseEntity<>("Nuovo utente registrato", 200);
        }
        else{
            return new ResponseEntity<>("Utente già esistente", 400);
        }
    }

    @PostMapping("/loginUser")
    public ResponseEntity<User> loginUser(@RequestBody User user){
        System.out.println("Richiesta login da: "+user.getName()+"   "+user.getPassword());
        if(userDao.checkUserLogin(user)){
            return new ResponseEntity<>(user, "Login eseguito", 200);
        }
        else{
            return new ResponseEntity<>("Credenziali errate", 400);
        }
    }

    @PostMapping("/deleteUser")
    public ResponseEntity<Void> deleteUser(@RequestBody UserDelete request){
        if(userDao.checkUserDelete(request.getUser(), request.getPassword()))
            return new ResponseEntity<>("Utente eliminato", 200);
        
        return new ResponseEntity<>("Password errata", 400);
    }
}
