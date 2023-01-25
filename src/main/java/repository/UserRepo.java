package repository;

import model.User;

import java.util.Collection;
import java.util.Optional;

public interface UserRepo {

    void addUser(User user);
    Optional<User> findUserById(String userId);
    Collection<User> findAll();
}
