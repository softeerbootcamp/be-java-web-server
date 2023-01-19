package servlet;

import db.Database;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.HttpRequest;
import response.CreateResponse;
import response.HttpResponse;
import response.StatusCode;
import service.UserService;

import java.io.IOException;
import java.net.URISyntaxException;

public class UserCreate implements Servlet{
    
    private static Logger logger = LoggerFactory.getLogger(UserCreate.class);
    
    @Override
    public HttpResponse service(HttpRequest httpRequest) {
        if (httpRequest.isGet()) {
            get(httpRequest);
        }
        if (httpRequest.isPost()) {
            return post(httpRequest);
        }
        return null;
    }

    @Override
    public HttpResponse post(HttpRequest httpRequest) {
        try {
            User user = UserService.from(httpRequest).postJoinService();
            Database.addUser(user);
            return CreateResponse.
                    found("/index.html")
                    .build();
        } catch (RuntimeException e) {
            logger.debug("회원가입란을 모두 입력하셔야 합니다.");
//            return HttpResponse.found().bodyByPath("./templates/index.html").build();
            return CreateResponse.
                    found("/user/form.html")
                    .build();
        }
    }

    @Override
    public void get(HttpRequest httpRequest) {

    }
}
