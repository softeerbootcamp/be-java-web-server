package service;

import db.UserDb;
import model.User;

import java.util.HashMap;

public class UserService implements Service {

    private final String[] userKey = {"userId", "password", "name", "email"};


    @Override
    public void saveData(HashMap<String, String> data) {
        User user = new User(data.get(userKey[0]), data.get(userKey[1]), data.get(userKey[2]), data.get(userKey[3]));
        UserDb.addUser(user);
    }
}
