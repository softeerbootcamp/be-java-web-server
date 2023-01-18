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

    Map<String, String> data;

    public UserService(Map<String, String> data) {
        this.data = data;
    }

    public static UserService from(HttpRequest httpRequest) {
        String body = httpRequest.getBody();
        Map<String, String> data = new HashMap<>();
        String[] inputs = body.split("&");
        for (String input : inputs) {
            String[] keyAndValue = input.split("=");
            if (keyAndValue.length < 2) {
                throw new RuntimeException("빈 칸이 없어야합니다.");
            }
            logger.debug("key : {}", keyAndValue[0]);
            logger.debug("value : {}", keyAndValue[1]);
            data.put(keyAndValue[0], keyAndValue[1]);
        }
        return new UserService(data);
    }

    public User postJoinService() {
        String userId = data.get("userId");
        String password = data.get("password");
        String name = data.get("name");
        String email = data.get("email");

        return new User(userId, password, name, email);
    }

    public void postlogin() {
        String userId = data.get("userId");
        String password = data.get("password");

        User databaseuserId = Database.findUserById(userId);
        String databasePassword = databaseuserId.getPassword();

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
