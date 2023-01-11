package db;

import com.google.common.collect.Maps;
import exception.DuplicateUserIdException;
import model.User;

import java.util.Collection;
import java.util.Map;

public class Database {
    private static Map<String, User> users = Maps.newHashMap();

    public static void addUser(User user) {

        if (users.containsKey(user.getUserId())) {
            throw new DuplicateUserIdException();
        }
        users.put(user.getUserId(), user);
    }

    public static User findUserById(String userId) {
        return users.get(userId);
    }

    public static Collection<User> findAll() {
        return users.values();
    }
}
