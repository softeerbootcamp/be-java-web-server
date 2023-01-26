package service;

import db.Database;
import db.JdbcTemplate;
import model.User;
import java.util.Map;

public class UserService {
    private static UserService userService;
    private JdbcTemplate jdbcTemplate = JdbcTemplate.getInstance();

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
        jdbcTemplate.insertIntoUserDb(user);
    }
    public boolean login(User user) {
        User dbUser = jdbcTemplate.findUserByUserId(user.getUserId());
        if (dbUser == null) {
            return false;
        }
        if (dbUser.getPassword().equals(user.getPassword())) {
            return true;
        }
        return false;
    }
}
