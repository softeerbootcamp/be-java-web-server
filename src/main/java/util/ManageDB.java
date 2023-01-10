package util;

import db.Database;
import model.User;

import java.util.Map;

public class ManageDB {
    public static void saveUser(Map<String,String> querys){
        User user = new User(querys.get("userId"), querys.get("password"), querys.get("name"), querys.get("email"));
        Database.addUser(user);
    }
    public static User findUserById(String userId){
        return Database.findUserById(userId);
    }

}
