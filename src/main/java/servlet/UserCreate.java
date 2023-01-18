package servlet;

import db.Database;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.HttpRequest;
import response.StatusLine;
import service.UserService;

public class UserCreate implements Servlet{
    
    private static Logger logger = LoggerFactory.getLogger(UserCreate.class);
    
    @Override
    public StatusLine service(HttpRequest httpRequest) {
        if (httpRequest.isGet()) {
            try{
                get(httpRequest);
                return StatusLine.Found;
            } catch (RuntimeException e){
                logger.debug("[UserCreate] runtimeException");
                return StatusLine.NotJoin;
            }
        }

        if (httpRequest.isPost()) {
            try{
                post(httpRequest);
                return StatusLine.Found;
            } catch (RuntimeException e){
                logger.debug("[UserCreate] runtimeException");
                return StatusLine.NotJoin;
            }
        }
        return null;
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
