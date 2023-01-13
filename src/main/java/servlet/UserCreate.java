package servlet;

import db.Database;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.HttpRequest;

import java.util.HashMap;
import java.util.Map;

public class UserCreate implements Servlet{
    
    private static Logger logger = LoggerFactory.getLogger(UserCreate.class);
    
    @Override
    public void service(HttpRequest httpRequest) {
        logger.debug("Method : {}", httpRequest);
        if (httpRequest.isGet()) {
            get(httpRequest);
            return;
        }

        if (httpRequest.isPost()) {
            post(httpRequest);
            return;
        }
    }

    @Override
    public void post(HttpRequest httpRequest) {

    }

    @Override
    public void get(HttpRequest httpRequest) {
        Map<String, String> parameters = httpRequest.getParameters();
        String userId = parameters.get("userId");
        String password = parameters.get("password");
        String name = parameters.get("name");
        String email = parameters.get("email");

        if (userId.isEmpty() || password.isEmpty() || name.isEmpty() || email.isEmpty()) {
            return;
        }

        User user = new User(userId, password, name, email);
        Database.addUser(user);
    }
}
