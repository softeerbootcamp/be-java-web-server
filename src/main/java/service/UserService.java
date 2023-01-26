package service;

import db.UserDatabase;
import model.User;

import java.sql.SQLException;
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

    public Optional<User> findUserById(String userId) throws SQLException {
        return userDatabase.findUserById(userId);
    }

    public Optional<User> findUserByIdAndPwd(String userId, String pwd) throws SQLException {
        return userDatabase.findUserByIdAndPwd(userId, pwd);
    }

    public void addUser(User user) throws IllegalArgumentException, SQLException {
        if(findUserById(user.getId()).isPresent()) {
            throw new IllegalArgumentException();
        }
        userDatabase.addUser(user);
    }

    public Collection<User> findAllUsers() throws SQLException {
        return userDatabase.findAll();
    }
}
