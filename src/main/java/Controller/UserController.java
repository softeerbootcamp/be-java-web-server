package Controller;

import http.HttpRequest;
import http.HttpResponse;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;
import webserver.RequestHandler;

import java.util.Map;

public class UserController implements Controller{
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Override
    public void makeResponse(HttpRequest httpRequest, HttpResponse httpResponse) {
        // 회원가입일 때
        String uri = httpRequest.getUri();
        // TODO 할 일 enum 으로 구현 가능 할듯
        if (uri.startsWith("/user/create")) {
            int index = uri.indexOf("?");
            String requestPath = uri.substring(0, index);
            String queryString = uri.substring(index + 1);
            logger.debug("QueryString : {}", queryString);
            Map<String, String> params = HttpRequestUtils.parseQueryString(queryString);
            User user = new User(params.get("userId"), params.get("password"), params.get("name"), params.get("email"));
            logger.debug("User : {}", user);
        }
    }
}
