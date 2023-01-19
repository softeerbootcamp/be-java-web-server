package servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.HttpRequest;
import response.CreateResponse;
import response.HttpResponse;
import response.StatusCode;
import service.UserService;

import javax.naming.AuthenticationException;
import java.io.IOException;
import java.net.URISyntaxException;

public class Userlogin implements Servlet{
    /*
    * TODO
    *  기능 구현중
    * */

    private static Logger logger = LoggerFactory.getLogger(UserCreate.class);

    @Override
    public HttpResponse service(HttpRequest httpRequest) throws IOException, URISyntaxException {
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
    public HttpResponse post(HttpRequest httpRequest) throws IOException, URISyntaxException {
        try {
            UserService.from(httpRequest).postlogin();
//            return StatusCode.CustomLogin;
            return CreateResponse.ok()
                    .bodyByPath("./templates/index.html").
                    setCookie("JSESSIONID", "1234", "/").
                    build();
        } catch (AuthenticationException e) {
            return CreateResponse.ok()
                    .bodyByPath("./templates/user/login_failed.html")
                    .setCookie("JSESSIONID", "1234", "/")
                    .build();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }
}
