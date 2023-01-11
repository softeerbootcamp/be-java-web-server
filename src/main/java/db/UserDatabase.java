package db;

import com.google.common.collect.Maps;

import model.User;

import java.util.Collection;
import java.util.Map;

public class UserDatabase implements Database{
    private static Map<String, User> users = Maps.newHashMap();


    @Override
    public <T> void addData(T objects) {
        User user = (User) objects;
        users.put(user.getUserId(), user);
    }

    @Override
    public <T> T findObjectById(Object userId) {
        T user = (T) users.get((String) userId);
        return user;
    }

    @Override
    public <T> Collection<T> findAll() {
        return (Collection<T>) users.values();
    }


}
