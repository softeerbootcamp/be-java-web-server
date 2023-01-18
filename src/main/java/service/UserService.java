package service;

import db.Database;
import model.User;

import java.util.Map;

public class UserService {
    private static final String USER_ID = "userId";
    private static final String PASSWORD = "password";
    private static final String NAME = "name";
    private static final String EMAIL = "email";

    private UserService(){
    }

    private static class UserServiceHolder {
        private static final UserService USER_SERVICE = new UserService();
    }

    public static UserService getInstance() {
        return UserServiceHolder.USER_SERVICE;
    }


    public void create(Map<String, String> parameters) {
        User user = new User(
                parameters.get(USER_ID),
                parameters.get(PASSWORD),
                parameters.get(NAME),
                parameters.get(EMAIL)
        );
        Database.addUser(user);
    }
}
