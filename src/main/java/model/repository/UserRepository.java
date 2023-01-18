package model.repository;

import model.domain.User;

import java.util.Collection;
import java.util.Optional;

public interface UserRepository {

    User addUser(User user);

    Optional<User> findUserById(String userId);

    Collection<User> findAll();
}
