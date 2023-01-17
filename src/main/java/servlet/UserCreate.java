package servlet;

import db.Database;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.HttpRequest;
import service.UserService;

import java.util.HashMap;
import java.util.Map;

public class UserCreate implements Servlet{
    
    private static Logger logger = LoggerFactory.getLogger(UserCreate.class);
    
    @Override
    public void service(HttpRequest httpRequest) {
        if (httpRequest.isGet()) {
            logger.debug("GET 수행");
            get(httpRequest);
            return;
        }

        if (httpRequest.isPost()) {
            logger.debug("POST 수행");
            post(httpRequest);
            return;
        }
    }

    @Override
    public void post(HttpRequest httpRequest) {
        logger.debug("DoPost");

        User user = UserService.postJoinService(httpRequest);
        logger.debug("[User Create] POST user data : {}", user);

        Database.addUser(user);
    }

    @Override
    public void get(HttpRequest httpRequest) {
        logger.debug("DoGet");

        User user = UserService.getJoinService(httpRequest);
        logger.debug("[User Create] GET user data : {}", user);

        Database.addUser(user);
    }
}
