package service;

import db.Database;
import model.User;

import java.util.Optional;

public class LoginService {

    public boolean checkRightUser(String userId, String password) throws NullPointerException{
        User user = Database.findUserById(userId);
        if(user.getPassword().equals(password)) return true;
        return false;
    }

}
