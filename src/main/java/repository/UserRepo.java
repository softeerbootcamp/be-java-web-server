package repository;

import model.User;

import java.util.Collection;
import java.util.Optional;

public interface UserRepo {
    UserRepo instance = new DBUserRepo();

    static UserRepo getInstance() {
        return instance;
    }

    void addUser(User user);

    Optional<User> findUserById(String userId);

    Collection<User> findAll();

    void delete(String userId);
}
