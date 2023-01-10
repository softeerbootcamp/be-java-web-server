package util;

import db.Database;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class ManageDB {
    private static final Logger logger = LoggerFactory.getLogger(ManageDB.class);
    public static void saveUser(Map<String,String> querys){
        User user = new User(querys.get("userId"), querys.get("password"), querys.get("name"), querys.get("email"));
        Database.addUser(user);
        logger.debug("save user: "+user.toString());
    }
    public static User findUserById(String userId){
        return Database.findUserById(userId);
    }
}
