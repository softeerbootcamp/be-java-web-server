package webserver.service;

import customException.cannotLogIn.CannotLogInException;
import db.Database;
import webserver.httpUtils.Request;
import webserver.httpUtils.Response;

import java.util.Map;
import model.User;

public class LogInService implements Service{
    @Override
    public Response exec(Request req) {
        Map<String, String> userInfo = getUserInfo(req);

        try{
            if(null == Database.findUserById(userInfo.get(User.ID))){

            }
        } catch(CannotLogInException e)
        {}

        return null;
    }
}
