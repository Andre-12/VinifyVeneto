package com.server.server.model.user;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserDao {
    
    @Autowired
    private UserRepository repository;

    public void addUser(User user){
        repository.save(user);
    }

    public boolean checkUser(User user){
        return repository.findById(user.getName()).isPresent();
    }

    public boolean checkUserLogin(User user){
        if(checkUser(user)){
            User temp = repository.findById(user.getName()).get();
            if(temp.getPassword().equals(user.getPassword())){
                return true;
            }
            else{
                return false;
            }
        }
        else{
            return false;
        }
    }

    public boolean checkUserDelete(String id, String password){
        Optional<User> tmp = repository.findById(id);
        if(tmp.isPresent()){
            User u = tmp.get();
            if(u.getPassword().equals(password)){
                repository.deleteById(id);
                return true;
            }
            else{
                return false;
            }
        }
        else{
            return false;
        }
    }
}
