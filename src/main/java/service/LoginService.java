package service;

import db.Database;
import model.User;

import java.util.Optional;

public class LoginService {

    private static LoginService instance;

    public static LoginService getInstance() {
        if (instance == null) {
            synchronized (LoginService.class) {
                instance = new LoginService();
            }
        }
        return instance;
    }


    public boolean checkRightUser(String userId, String password) throws NullPointerException{
        User user = Database.findUserById(userId);
        if(user.getPassword().equals(password)) return true;
        return false;
    }

}
