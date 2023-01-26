package db.tmpl;

import model.User;

import java.util.List;
import java.util.Optional;

public interface UserDatabase {

    void addUser(User user);

    Optional<User> findUserById(String userId);

    List<User> findAll();
}
