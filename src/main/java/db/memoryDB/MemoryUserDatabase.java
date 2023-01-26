package db.memoryDB;

import com.google.common.collect.Maps;
import db.tmpl.UserDatabase;
import model.User;
import java.util.*;

public class MemoryUserDatabase implements UserDatabase {
    private static Map<String, User> users = Maps.newHashMap();

    static{
        users.put("admin", new User("admin", "123", "admin", "admin@admin.com"));
    }
    @Override
    public void addUser(User user) {
        users.put(user.getUserId(), user);
    }

    @Override
    public Optional<User> findUserById(String userId) {
        return Optional.ofNullable(users.get(userId));
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }
}
