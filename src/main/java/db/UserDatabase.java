package db;

import com.google.common.collect.Maps;

import model.User;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

public class UserDatabase implements Database<User>{
    private static Map<String, User> users = Maps.newHashMap();

    @Override
    public Optional<User> findObjectById(String userId) {
        return Optional.ofNullable(users.get(userId));
    }

    @Override
    public void addData(User user) {
        users.put(user.getUserId(), user);
    }

    @Override
    public Collection<User> findAll() {
        return users.values();
    }

}
