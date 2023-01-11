package model.repository;

import com.google.common.collect.Maps;

import model.domain.User;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public class MemoryUserRepository implements UserRepository{
    private static Map<String, User> users = Maps.newHashMap();

    public User addUser(User user) {
        users.put(user.getUserId(), user);
        return user;
    }

    public Optional<User> findUserById(String userId) {
        return Optional.ofNullable(users.get(userId));
    }

    public Collection<User> findAll() {
        return users.values();
    }
}
