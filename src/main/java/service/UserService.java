package service;

import db.Database;
import db.JdbcTemplate;
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
        User user = new User(parsedUserMap.get("userId"), parsedUserMap.get("password"), parsedUserMap.get("name"), parsedUserMap.get("email"));
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.insertIntoUserDb(user);
        Database.addUser(user);
    }
    public boolean login(User user) {
        User dbUser = Database.findUserById(user.getUserId());
        if (dbUser == null) {
            return false;
        }
        if (dbUser.getPassword().equals(user.getPassword())) {
            return true;
        }
        return false;
    }
}
