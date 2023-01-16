package controller;

import db.Database;
import http.HttpStatus;
import http.request.HttpRequest;
import http.response.HttpResponse;
import http.response.HttpStatusLine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.SignUpService;
import util.HttpResponseUtils;

import java.io.IOException;

public class UserController implements Controller {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Override
    public HttpResponse makeResponse(HttpRequest httpRequest) throws IOException {
        // Uri  받아옵시다
        String uri = httpRequest.getUri();

        // 회원가입일 때
        if (isSignUpService(uri)) {
            // user 정보 받아서 데이터베이스에 입력
            Database.addUser(SignUpService.makeUserInfo(httpRequest.getBody()));
            // 302 응답이라 location만 필요하기 때문에 body랑 contentType는 없음!
            return new HttpResponse.HttpResponseBuilder()
                    .setHttpStatusLine(new HttpStatusLine(HttpStatus.FOUND, httpRequest.getHttpVersion()))
                    .build();
        }

        //TODO 임시 코드 - return 예외처리 해야됨
        return null;
    }

    public boolean isSignUpService(String uri){
        return uri.startsWith("/user/create");
    }

}
