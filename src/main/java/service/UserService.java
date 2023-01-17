package service;

import db.Database;
import model.domain.User;

import java.util.Objects;

public class UserService {
    private final Database database;

    public UserService(Database database) {
        this.database = database;
    }

    public void signUp(User user) {
        database.addUser(user);
    }

    public boolean logIn(User user) {
        User findUser = database.findUserById(user.getUserId());
        return Objects.nonNull(findUser) && findUser.getPassword().equals(user.getPassword());
    }
}
