package db;

import model.User;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MemoryUserDatabase implements UserDatabase {
    private final static Map<String, User> users = new ConcurrentHashMap<>();
    private static UserDatabase userDatabase;

    private MemoryUserDatabase() {
    }

    public static UserDatabase getInstance() {
        if (userDatabase == null) userDatabase = new MemoryUserDatabase();
        return userDatabase;
    }

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
