package db.mysql;

import model.User;

import java.util.Collection;

public class DB_Users {

    public static User NOT_FOUND_USER = new User("", "", "" ,"");

    public static void addUser(User user) {
        DBUtils
    }

    public static User findUserById(String userId) {
        if(users.containsKey(userId))
            return users.get(userId);
        return NOT_FOUND_USER;
    }

    public static Collection<User> findAll() {
        return users.values();
    }
}
