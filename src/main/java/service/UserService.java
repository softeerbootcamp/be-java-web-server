package service;

import db.Database;
import db.SessionDatabase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.HttpRequest;
import request.HttpSession;
import response.HttpResponse;

import javax.naming.AuthenticationException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class UserService {

    private static Logger logger = LoggerFactory.getLogger(UserService.class);

    Map<String, String> data;
    HttpRequest httpRequest;

    public UserService(HttpRequest httpRequest) {
        data = new HashMap<>();
        this.httpRequest = httpRequest;
    }

    public UserService(Map<String, String> data, HttpRequest httpRequest) {
        this.data = data;
        this.httpRequest = httpRequest;
    }

    public static UserService from(HttpRequest httpRequest) {
        String body = httpRequest.getBody();
        logger.debug("body : {}", body);
        Map<String, String> data = new HashMap<>();
        String[] inputs = body.split("&");
        for (String input : inputs) {
            logger.debug("bodyParam : {}", input);
            String[] keyAndValue = input.split("=");
            if (keyAndValue.length < 2) {
                continue;
            }
            logger.debug("key : {}", keyAndValue[0]);
            logger.debug("value : {}", keyAndValue[1]);
            data.put(keyAndValue[0].trim(), keyAndValue[1].trim());
        }
        return new UserService(data,httpRequest);
    }

    public User postJoinService() {
        String userId = data.get("userId");
        String password = data.get("password");
        String name = data.get("name");
        String email = data.get("email");

        return new User(userId, password, name, email);
    }

    public HttpResponse postLoginService() throws IOException, URISyntaxException {
        String userId = data.get("userId");
        String password = data.get("password");

        logger.debug("PostuserId:{}",userId);
        logger.debug("Postuserpwd:{}",password);

        User user = Database.findUserById(userId);

        HttpSession httpSession = httpRequest.getHttpSession();
        httpSession.setAttribute("user", user);
        SessionDatabase.addHttpSession(httpSession);

        if (user != null && password.equals(user.getPassword())) {
            return HttpResponse.ok()
                    .bodyByPath("./templates/index.html")
                    .setCookie("JSESSIONID",  httpRequest.getSessionId(),"/")
                    .build();
        }
        return HttpResponse.ok()
                .bodyByPath("./templates/user/login_failed.html")
                .setCookie("JSESSIONID",  httpRequest.getSessionId(), "/")
                .build();
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
