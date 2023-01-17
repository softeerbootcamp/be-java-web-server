package service;

import db.Database;
import db.Session;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;

import java.util.Map;

public class LogInService {
    private static final Logger logger = LoggerFactory.getLogger(LogInService.class);



    public static boolean isLoginSuccess(Map<String, String> params) {
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

    public static String addSessionAndGetSessionID(String userId) {
        Session.addSession(userId);
        return Session.findSessionIdByUserId(userId);
    }
}
