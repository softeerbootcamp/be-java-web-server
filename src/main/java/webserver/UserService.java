package webserver;

import db.Database;
import model.request.Request;
import model.User;

import java.util.Map;

public class UserService {
    public void signUpUser(Request request) {
        Map<String, String> requestParams = request.getRequestParams();
        User user = new User(requestParams.get("userId"),
                requestParams.get("password"),
                requestParams.get("name"),
                requestParams.get("email"));
        Database.addUser(user);
    }
}
