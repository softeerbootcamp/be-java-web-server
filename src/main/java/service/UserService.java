package service;

import db.UserDatabase;
import model.User;

import java.util.Collection;
import java.util.Optional;

public class UserService {
    private static final UserService instance;

    private final UserDatabase userDatabase;

    static {
        instance = new UserService();
    }

    private UserService() {
        userDatabase = UserDatabase.getInstance();
    }

    public static UserService getInstance() {
        return instance;
    }

    public Optional<User> findUser(String userId) {
        return userDatabase.findUserById(userId);
    }

    public void addUser(User user) {
        userDatabase.addUser(user);
    }

    public Collection<User> findAllUsers() {

        return userDatabase.findAll();
    }
}
