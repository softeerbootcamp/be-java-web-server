package webserver;

import db.Database;
import model.User;

import java.util.Map;

public class UserService {


    public void signUpUser(Map<String, String> requestParams) {
        User user = new User(requestParams.get("userId"),
                requestParams.get("password"),
                requestParams.get("name"),
                requestParams.get("email"));
        Database.addUser(user);
    }
}
