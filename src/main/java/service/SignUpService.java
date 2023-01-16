package service;

import controller.UserController;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;

import java.util.Map;
import java.util.regex.Pattern;

public class SignUpService {
    private static final Logger logger = LoggerFactory.getLogger(SignUpService.class);
    public static User makeUserInfo(String uri) {
        // 회원 가입 정보들을 Map에 담는 과정
        // uri에서 ? 문자 뒤에 있는 query string을 분리 > query string을 & 문자 스플릿 해서 map 자료구조에 넣어줌
        Map<String, String> params = HttpRequestUtils.parseQueryString(uri);

        User user = new User(
                params.get("userId"),
                params.get("password"),
                params.get("name"),
                params.get("email"));

        logger.debug("User : {}", user);

        return user;
    }
}
