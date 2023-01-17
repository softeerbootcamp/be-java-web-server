package webserver.service;

import db.Database;
import db.SessionDb;
import exception.UserNotFoundException;
import model.User;
import model.request.Request;
import model.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.controller.UserLoginController;

import java.util.Map;
import java.util.UUID;

import static model.response.HttpStatusCode.FOUND;

public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public void signUpUser(Request request) {
        Map<String, String> requestParams = request.getRequestParams();
        User user = new User(requestParams.get("userId"),
                requestParams.get("password"),
                requestParams.get("name"),
                requestParams.get("email"));
        Database.addUser(user);
    }

    public Response loginUser(Request request) throws UserNotFoundException {
        User byUser = Database.findUserById(request.getRequestParams().get("userId"))
                .orElseThrow(UserNotFoundException::new);

        boolean isValid = byUser.getPassword().equals(request.getRequestParams().get("password"));
        if (isValid) {
            String sid = String.valueOf(UUID.randomUUID());
            SessionDb.addSession(sid, byUser);
            logger.debug("로그인 성공 (세션 저장) user id : {}, sid : {}", byUser.getUserId(), sid);

            return Response.of(request.getHttpVersion(), FOUND, Map.of("Set-Cookie", "sid=" + sid + "; Path=/",
                    "Location", "/index.html"), new byte[0]);
        }
        return Response.of(request.getHttpVersion(), FOUND, Map.of("Location", "/user/login_failed.html"), new byte[0]);
    }

}
