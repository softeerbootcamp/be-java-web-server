package service;

import db.UserDatabase;
import model.User;

import java.util.Optional;

public class UserService {
    private static final UserService instance;

    private final UserDatabase userDatabase = UserDatabase.getInstance();

    static {
        instance = new UserService();
    }

    private UserService() {}

    public static UserService getInstance() {
        return instance;
    }

    public Optional<User> findUser(String userId) {
        return userDatabase.findUserById(userId);
    }

    public void addUser(User user) {
        userDatabase.addUser(user);
    }
}
