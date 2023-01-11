package service;

import db.Database;
import db.UserDatabase;
import model.User;

import java.util.HashMap;

public class UserService implements Service {

    private final String[] userKey = {"userId", "password", "name", "email"};
    Database database = new UserDatabase();


    @Override
    public <T> void saveData(HashMap<T, T> data) {
        User user = new User((String) data.get(userKey[0]), (String) data.get(userKey[1]), (String) data.get(userKey[2]), (String) data.get(userKey[3]));
        database.addData(user);
    }
}
