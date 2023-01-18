package servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.HttpRequest;
import response.ResponseHeader;
import response.StatusLine;
import service.UserService;

import javax.naming.AuthenticationException;
import java.util.HashMap;
import java.util.Map;

public class Userlogin implements Servlet{
    /*
    * TODO
    *  기능 구현중
    * */

    private static Logger logger = LoggerFactory.getLogger(UserCreate.class);

    @Override
    public StatusLine service(HttpRequest httpRequest) {
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
    public StatusLine post(HttpRequest httpRequest) {
        try {
            UserService.from(httpRequest).postlogin();
            return StatusLine.CustomLogin;
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return StatusLine.TemporaryRedirect;
        }
    }
}
