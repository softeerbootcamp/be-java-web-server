package controller;

import db.Database;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.HttpRequest;
import response.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class LoginController extends AbstractController{

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Override
    public HttpResponse get(HttpRequest httpRequest) {
        return null;
    }

    @Override
    public HttpResponse post(HttpRequest httpRequest) throws IOException, URISyntaxException {
        Map<String, String> data = new HashMap<>();
        String body = httpRequest.getBody();
        logger.debug("body : {}", body);
        String[] inputs = body.split("&");
        for (String input : inputs) {
            logger.debug("bodyParam : {}", input);
            String[] keyAndValue = input.split("=");
            if (keyAndValue.length < 2) {
                continue;
            }
            logger.debug("key : {}", keyAndValue[0]);
            logger.debug("value : {}", keyAndValue[1]);
            data.put(keyAndValue[0], keyAndValue[1]);
        }

        String userId = data.get("userId");
        String password = data.get("password");

        User user = Database.findUserById(userId);

//        HttpSession httpSession = httpRequest.getHttpSession();
//        httpSession.setAttribute("user", user);
//        SessionDataBase.addHttpSession(httpSession);

        if (user != null && password.equals(user.getPassword())) {
            return HttpResponse.ok()
                    .bodyByPath("./templates/index.html")
                    .setCookie("JSESSIONID", "1234","/")
                    .build();
        }

        return HttpResponse.ok()
                .bodyByPath("./templates/user/login_failed.html")
                .setCookie("JSESSIONID", "1234", "/")
                .build();
    }
}
