package service;

import db.Database;
import model.domain.User;

public class UserService {
    private final Database database;

    public UserService(Database database) {
        this.database = database;
    }

    public void signUp(User user) {
        database.addUser(user);
    }
}
