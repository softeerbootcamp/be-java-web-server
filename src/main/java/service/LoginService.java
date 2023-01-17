package service;

import db.Database;
import model.User;

public class LoginService {

    public boolean checkRightUser(String userId, String password){
        User user = Database.findUserById(userId);
        if(user != null){
            if(user.getPassword().equals(password)) return true;
        }
        return false;
    }

}
