package webserver;

import db.Database;
import model.User;
import util.HttpRequestUtils;

import java.util.Map;

public class UserService {


    public void signUpUser(String queryString) {
        Map<String, String> requestParams = HttpRequestUtils.parseQuerystring(queryString);

        User user = new User(requestParams.get("userId"),
                requestParams.get("password"),
                requestParams.get("name"),
                requestParams.get("email"));
        Database.addUser(user);
    }
}
