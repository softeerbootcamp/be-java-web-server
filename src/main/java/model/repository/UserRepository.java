package model.repository;

import model.domain.User;

import java.util.Collection;

public interface UserRepository {
    void addUser(User user);

    User findUserById(String userId);

    Collection<User> findAll();
}
