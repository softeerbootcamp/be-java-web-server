package db;

import com.google.common.collect.Maps;

import model.domain.User;

import java.util.Collection;
import java.util.Map;

public class Database {
    private static final Map<String, User> users = Maps.newHashMap();

    public void addUser(User user) {
        users.put(user.getUserId(), user);
    }

    public User findUserById(String userId) {
        return users.get(userId);
    }

    public Collection<User> findAll() {
        return users.values();
    }
}
