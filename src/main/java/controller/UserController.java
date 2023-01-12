package controller;

import db.Database;
import http.request.HttpRequest;
import http.response.HttpResponse;
import http.HttpStatus;
import http.response.HttpStatusLine;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.SignUpService;
import util.HttpRequestUtils;
import util.HttpResponseUtils;

import java.io.IOException;
import java.util.Map;
import java.util.regex.Pattern;

public class UserController implements Controller {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Override
    public HttpResponse makeResponse(HttpRequest httpRequest) throws IOException {
        // Uri  받아옵시다
        String uri = httpRequest.getUri();

        // TODO 할 일 enum 으로 구현 가능 할듯
        
        // 회원가입일 때
        if (isSignUpService(uri)) {
            // user 정보 받아서 데이터베이스에 입력
            Database.addUser(SignUpService.makeUserInfo(uri));
            // 302 응답이라 location만 필요하기 때문에 body랑 contentType는 없음!
            return new HttpResponse(new HttpStatusLine(HttpStatus.FOUND, httpRequest.getHttpVersion()), null, null);
        }

        //TODO 임시 코드 - return 예외처리 해야됨
        byte[] responseBody = HttpResponseUtils.makeBody(httpRequest.getUri(), null);
        return new HttpResponse(new HttpStatusLine(HttpStatus.OK, httpRequest.getHttpVersion()), responseBody, null);
    }

    public boolean isSignUpService(String uri){
        return uri.startsWith("/user/create");
    }

}
