package service;

import controller.UserController;
import db.Database;
import http.HttpStatus;
import http.response.HttpResponse;
import http.response.HttpStatusLine;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;

import java.util.Map;
import java.util.regex.Pattern;

public class SignUpService{
    private static final Logger logger = LoggerFactory.getLogger(SignUpService.class);
    public static User makeUserByParams(Map<String, String> params) {
        return new User(
                params.get("userId"),
                params.get("password"),
                params.get("name"),
                params.get("email"));
    }
    public static void addDatabase(User user){
        Database.addUser(user);
    }

    public static HttpResponse service(Map<String, String> params, String httpVersion) {
        // user 정보 받아서 데이터베이스에 입력
        SignUpService.addDatabase(SignUpService.makeUserByParams(params));
        // 302 응답이라 location만 필요하기 때문에 body랑 contentType는 없음!
        return new HttpResponse.HttpResponseBuilder()
                .setHttpStatusLine(new HttpStatusLine(HttpStatus.FOUND, httpVersion))
                .set302Header("/index.html")
                .build();
    }
}
