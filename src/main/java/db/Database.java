package db;

import com.google.common.collect.Maps;

import model.domain.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Map;

public class Database {
    private static final Logger logger = LoggerFactory.getLogger(Database.class);

    private static final Map<String, User> users = Maps.newHashMap();

    public void addUser(User user) {
        users.put(user.getUserId(), user);

        for(User testUser : users.values()) {
            logger.debug("userId: {}, password: {}, name: {}, email: {}",
                    testUser.getUserId(), testUser.getPassword(), testUser.getName(), testUser.getEmail());
        }
    }

    public User findUserById(String userId) {
        return users.get(userId);
    }

    public Collection<User> findAll() {
        return users.values();
    }
}
