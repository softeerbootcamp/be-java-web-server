package service;

import controller.UserController;
import db.Database;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;

import java.util.Map;
import java.util.regex.Pattern;

public class SignUpService{
    private static final Logger logger = LoggerFactory.getLogger(SignUpService.class);
    public static User makeUserByParams(Map<String, String> params) {
        return new User(
                params.get("userId"),
                params.get("password"),
                params.get("name"),
                params.get("email"));
    }
    public static void addDatabase(User user){
        Database.addUser(user);
    }
}
