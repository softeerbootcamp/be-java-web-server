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

    public Optional<User> findUserById(String userId) throws SQLException, NullPointerException {
        return userDatabase.findUserById(userId);
    }

    public Optional<User> findUserByIdAndPwd(String userId, String pwd) throws SQLException, NullPointerException {
        return userDatabase.findUserByIdAndPwd(userId, pwd);
    }

    public void addUser(User user) throws IllegalArgumentException, SQLException, NullPointerException {
        if(findUserById(user.getUserId()).isPresent()) {
            throw new IllegalArgumentException();
        }
        userDatabase.addUser(user);
    }

    public Collection<User> findAllUsers() throws SQLException, NullPointerException {
        return userDatabase.findAll();
    }
}
