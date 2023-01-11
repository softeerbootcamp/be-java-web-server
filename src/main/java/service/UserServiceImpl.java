package service;

import db.Database;
import model.User;

public class UserServiceImpl implements UserService {
    private final Database database;

    public UserServiceImpl(Database database) {
        this.database = database;
    }

    @Override
    public void join(User user) {
        database.addUser(user);
    }

    @Override
    public User findUser(String userId) {
        return database.findUserById(userId);
    }
}
