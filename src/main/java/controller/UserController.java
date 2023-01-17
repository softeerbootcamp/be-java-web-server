package controller;

import db.Database;
import db.Session;
import http.HttpStatus;
import http.request.HttpRequest;
import http.response.HttpResponse;
import http.response.HttpStatusLine;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.LogInService;
import service.SignUpService;
import util.HttpRequestUtils;
import util.HttpResponseUtils;

import java.io.IOException;
import java.util.Map;

public class UserController implements Controller {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Override
    public HttpResponse makeResponse(HttpRequest httpRequest) {
        // Uri, httpVersion 받아옵시다
        String uri = httpRequest.getUri();
        String httpVersion = httpRequest.getHttpVersion();
        // body 에서 params 분리
        Map<String, String> params = HttpRequestUtils.parseQueryString(httpRequest.getBody());

        // 회원가입일 때
        if (isSignUpService(uri)) {
            return SignUpService.service(params, httpVersion);
        }

        // 로그인일 때
        if(isLoginService(uri)){
            return LogInService.service(params, httpVersion);
        }

        //TODO 임시 코드 - return 예외처리 해야됨
        return null;
    }

    public boolean isSignUpService(String uri){
        return uri.equals("/user/create");
    }

    public boolean isLoginService(String uri){
        return uri.equals("/user/login");
    }

}
