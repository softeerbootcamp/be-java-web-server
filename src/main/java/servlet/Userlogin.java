package servlet;

import db.Database;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.HttpRequest;
import response.StatusLine;
import service.UserService;

public class Userlogin implements Servlet{
    /*
    * TODO
    *  기능 구현중
    * */

    private static Logger logger = LoggerFactory.getLogger(UserCreate.class);

    @Override
    public StatusLine service(HttpRequest httpRequest) {
        if (httpRequest.isGet()) {
            try{
                get(httpRequest);
                return StatusLine.Found;
            } catch (RuntimeException e){
                logger.debug("[UserCreate] runtimeException");
                return StatusLine.NotFound;
            }
        }

        if (httpRequest.isPost()) {
            try{
                post(httpRequest);
                return StatusLine.Found;
            } catch (RuntimeException e){
                logger.debug("[UserCreate] runtimeException");
                return StatusLine.NotFound;
            }
        }
        return null;    }

    @Override
    public void get(HttpRequest httpRequest) {

    }

    @Override
    public void post(HttpRequest httpRequest) {
        logger.debug("DoPost");
        UserService.postlogin(httpRequest);
    }
}
