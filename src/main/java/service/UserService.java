package service;

import db.Database;
import model.User;

import java.util.Map;

public class UserService {
    private static UserService userService;
    private UserService() {
    }
    public static UserService getInstance() {
        if (userService == null) {
            return new UserService();
        }
        return userService;
    }

    public void addUser(Map<String, String> parsedUserMap) {
        Database.addUser(new User(parsedUserMap.get("userId"), parsedUserMap.get("password"), parsedUserMap.get("name"), parsedUserMap.get("email")));
    }
    public boolean login(User user) {
        User dbUser = Database.findUserById(user.getUserId());
        if (dbUser.getPassword().equals(user.getPassword())) {
            return true;
        }
        return false;
    }
}
