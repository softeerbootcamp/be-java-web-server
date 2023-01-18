package service;

import db.Database;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.HttpRequest;
import response.StatusLine;
import servlet.UserCreate;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class UserService {

    private static Logger logger = LoggerFactory.getLogger(UserService.class);

    public static User postJoinService(HttpRequest httpRequest) {
        String body = httpRequest.getBody();
        Map<String, String> data = new HashMap<>();

        logger.debug("body : {}", body);
        String[] inputs = body.split("&");
        for (String input : inputs) {
            logger.debug("bodyParam : {}", input);
            String[] keyAndValue = input.split("=");
            if (keyAndValue.length < 2) {
                throw new RuntimeException("모든 값을 입력해야합니다.");
            }
            logger.debug("key : {}", keyAndValue[0]);
            logger.debug("value : {}", keyAndValue[1]);
            data.put(keyAndValue[0], keyAndValue[1]);
        }

        String userId = data.get("userId");
        String password = data.get("password");
        String name = data.get("name");
        String email = data.get("email");

        return new User(userId, password, name, email);
    }

    public static User getJoinService(HttpRequest httpRequest) {
        Map<String, String> data = httpRequest.getParameters();

        String userId = data.get("userId");
        String password = data.get("password");
        String name = data.get("name");
        String email = data.get("email");

        if (userId.isEmpty() || password.isEmpty() || name.isEmpty() || email.isEmpty()) {
            throw new RuntimeException("[UserService] 모든 값을 입력해야 합니다");
        }


        return new User(userId, password, name, email);
    }

    public static void postlogin(HttpRequest httpRequest) {
        String body = httpRequest.getBody();
        Map<String, String> data = new HashMap<>();

        logger.debug("body : {}", body);

        String[] inputs = body.split("&");
        for (String input : inputs) {
            logger.debug("bodyParam : {}", input);
            String[] keyAndValue = input.split("=");
            if (keyAndValue.length < 2) {
                throw new RuntimeException("로그인과 비밀번호를 모두 입력하셔야 합니다.");
            }
            logger.debug("key : {}", keyAndValue[0]);
            logger.debug("value : {}", keyAndValue[1]);
            data.put(keyAndValue[0], keyAndValue[1]);
        }

        String userId = data.get("userId");
        String password = data.get("password");

        User databaseuserId = Database.findUserById(userId);
        String databasePassword = databaseuserId.getPassword();

        logger.debug("Input user : {}", userId);

        if(databaseuserId.getUserId().isEmpty()) throw new RuntimeException("가입되지 않은 회원입니다.");
        if(!password.equals(databasePassword)) throw new RuntimeException("비밀번호가 다릅니다.");
    }

    public static void showUserList() {
        Collection<User> allUser = Database.findAll();
        Iterator<User> it = allUser.iterator();
        while (it.hasNext()) {
            User user = it.next();
            logger.debug("save user : {}",user.getUserId());
            logger.debug("save user : {}",user.getPassword());
        }
    }
}
