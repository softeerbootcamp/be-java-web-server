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
                return StatusLine.SeeOther;
            }
        }

        if (httpRequest.isPost()) {
            try{
                post(httpRequest);
                return StatusLine.Found;
            } catch (RuntimeException e){
                return StatusLine.SeeOther;
            }
        }
        return null;
    }

    @Override
    public void post(HttpRequest httpRequest) {
        User user = UserService.from(httpRequest).postJoinService();
        Database.addUser(user);
    }

    @Override
    public void get(HttpRequest httpRequest) {

    }
}
