package db;

import model.User;

import java.util.Collection;

public interface Database {
    void addUser(User user);
    User findUserById(String userId);
    Collection<User> findAll();
}
