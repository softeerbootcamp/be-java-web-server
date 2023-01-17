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
        // Uri  받아옵시다
        String uri = httpRequest.getUri();
        // body 에서 params 분리
        Map<String, String> params = HttpRequestUtils.parseQueryString(httpRequest.getBody());

        // 회원가입일 때
        if (isSignUpService(uri)) {
            // user 정보 받아서 데이터베이스에 입력
            SignUpService.addDatabase(SignUpService.makeUserByParams(params));
            // 302 응답이라 location만 필요하기 때문에 body랑 contentType는 없음!
            return new HttpResponse.HttpResponseBuilder()
                    .setHttpStatusLine(new HttpStatusLine(HttpStatus.FOUND, httpRequest.getHttpVersion()))
                    .setDestination("/index.html")
                    .makeHeader()
                    .build();
        }

        // 로그인일 때
        if(isLoginService(uri)){
            // 성공
            if(LogInService.isLoginSuccess(params)){
                // index.html로 이동
                // HTTP 헤더의 쿠키 값을 SID = 세션 ID로 응답
                // 세션 ID는 적당한 크기의 무작위 숫자 또는 문자열
                // 서버는 세션 아이디에 해당하는 User 정보에 접근 가능해야 한다.
                String cookie = LogInService.addSessionAndGetSessionID(params.get("userId"));
                return new HttpResponse.HttpResponseBuilder()
                        .setHttpStatusLine(new HttpStatusLine(HttpStatus.FOUND, httpRequest.getHttpVersion()))
                        .setDestination("/index.html")
                        .makeHeader()
                        .addCookie(cookie)
                        .build();
            }

            // 실패
            // /user/login_failed.html로 이동
            return new HttpResponse.HttpResponseBuilder()
                    .setHttpStatusLine(new HttpStatusLine(HttpStatus.FOUND, httpRequest.getHttpVersion()))
                    .setDestination("/user/login_failed.html")
                    .makeHeader()
                    .build();

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
