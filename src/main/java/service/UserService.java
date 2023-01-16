package service;

import db.Database;
import model.domain.User;

import java.util.Map;
import java.util.Objects;

public class UserService {
    private final Database database;

    public UserService(Database database) {
        this.database = database;
    }

    public void signUp(User user) {
        database.addUser(user);
    }

    public boolean logIn(Map<String, String> userLoginInfo) {
        User user = database.findUserById(userLoginInfo.get("userId"));
        return Objects.nonNull(user);
    }
}
