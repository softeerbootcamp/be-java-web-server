package service;

import model.User;

import java.util.HashMap;

public class UserService implements Service<User> {

    private final String[] userKey = {"userId", "password", "name", "email"};


    @Override
    public User createModel(HashMap<String,String> userMap) {
        return new User(userMap.get(userKey[0]), userMap.get(userKey[1]), userMap.get(userKey[2]), userMap.get(userKey[3]));
    }


}
