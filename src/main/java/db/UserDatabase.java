package db;

import com.google.common.collect.Maps;

import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public class UserDatabase {
    private static final Logger logger = LoggerFactory.getLogger(UserDatabase.class);

    private static final UserDatabase instance;

    private final Map<String, User> users;

    private UserDatabase() {
        users = Maps.newHashMap();
    }

    static {
        instance = new UserDatabase();
    }

    public static UserDatabase getInstance() {
        return instance;
    }

    public void addUser(User user) throws IllegalArgumentException {
        if(users.containsKey(user.getUserId())) {
            throw new IllegalArgumentException();
        }
        logger.debug("user {} added", user.getUserId());
        users.put(user.getUserId(), user);
    }

    public Optional<User> findUserById(String userId) throws IllegalArgumentException {
        return Optional.ofNullable(users.get(userId));
    }

    public Collection<User> findAll() {
        return users.values();
    }
}
