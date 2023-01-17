package service;

import db.Database;
import db.Session;
import http.HttpStatus;
import http.response.HttpResponse;
import http.response.HttpStatusLine;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;

import java.util.Map;

public class LogInService {
    private static final Logger logger = LoggerFactory.getLogger(LogInService.class);

    public static HttpResponse service(Map<String, String> params, String httpVersion) {
        // 성공
        if(isLoginSuccess(params)){
            // index.html로 이동
            // HTTP 헤더의 쿠키 값을 SID = 세션 ID로 응답
            // 세션 ID는 적당한 크기의 무작위 숫자 또는 문자열
            // 서버는 세션 아이디에 해당하는 User 정보에 접근 가능해야 한다.
            String cookie = addSessionAndGetSessionID(params.get("userId"));
            return new HttpResponse.HttpResponseBuilder()
                    .setHttpStatusLine(new HttpStatusLine(HttpStatus.FOUND, httpVersion))
                    .setDestination("/index.html")
                    .makeHeader()
                    .addCookie(cookie)
                    .build();
        }

        // 실패
        // /user/login_failed.html로 이동
        return new HttpResponse.HttpResponseBuilder()
                .setHttpStatusLine(new HttpStatusLine(HttpStatus.FOUND, httpVersion))
                .setDestination("/user/login_failed.html")
                .makeHeader()
                .build();
    }

    private static boolean isLoginSuccess(Map<String, String> params) {
        User tryLoginUser = Database.findUserById(params.get("userId"));
        logger.debug("User : {}", tryLoginUser);

        // id가 없을 때
        if(tryLoginUser == null) {
            logger.debug("User not Found !!");
            return false;
        }

        // 비밀번호가 맞아서 로그인 성공
        if(tryLoginUser.getPassword().equals(params.get("password"))){
            logger.debug("Login success !!");
            return true;
        }

        // 비밀번호 틀림
        logger.debug("Login failed !!");
        return false;

    }

    private static String addSessionAndGetSessionID(String userId) {
        return Session.makeSessionIdAndAddUserId(userId);
    }


}
