package db;

import com.google.common.collect.Maps;
import exception.DuplicateUserIdException;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Map;

public class Database {
    private static final Logger logger = LoggerFactory.getLogger(Database.class);

    private static Map<String, User> users = Maps.newHashMap();

    public static void addUser(User user) {

        if (users.containsKey(user.getUserId())) {
            throw new DuplicateUserIdException();
        }
        users.put(user.getUserId(), user);
        logger.debug(">> User {} Saved", user.getUserId());
    }

    public static User findUserById(String userId) {
        return users.get(userId);
    }

    public static Collection<User> findAll() {
        return users.values();
    }
}
