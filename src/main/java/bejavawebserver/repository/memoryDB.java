package bejavawebserver.repository;

import bejavawebserver.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class memoryDB {
    private static final Logger logger = LoggerFactory.getLogger(memoryDB.class);
    private static final Map<String, User> users = new HashMap<>();

    public static void addUser(User user) {
        logger.debug("Database User input : {}", user);
        users.put(user.getUserId(), user);
    }

    public static User findUserById(String userId) {
        return users.get(userId);
    }

    public static Collection<User> findAll() {
        return users.values();
    }

    public static boolean checkDuplicate(User user) {
        return users.get(user.getUserId()) != null;
    }
}
