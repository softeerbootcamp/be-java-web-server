package servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.HttpRequest;
import response.StatusCode;
import service.UserService;

import javax.naming.AuthenticationException;

public class Userlogin implements Servlet{
    /*
    * TODO
    *  기능 구현중
    * */

    private static Logger logger = LoggerFactory.getLogger(UserCreate.class);

    @Override
    public StatusCode service(HttpRequest httpRequest) {
        if (httpRequest.isGet()) {
            get(httpRequest);
        }

        if (httpRequest.isPost()) {
            return post(httpRequest);
        }
        return null;
    }

    @Override
    public void get(HttpRequest httpRequest) {

    }

    @Override
    public StatusCode post(HttpRequest httpRequest) {
        try {
            UserService.from(httpRequest).postlogin();
            return StatusCode.CustomLogin;
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return StatusCode.TemporaryRedirect;
        }
    }
}
