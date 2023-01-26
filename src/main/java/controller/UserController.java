package controller;

import db.UserDatabase;
import dto.HttpRequest;
import dto.HttpResponse;
import dto.SessionCookie;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.SessionService;
import service.UserService;
import utility.HttpMethodType;
import utility.HttpRequestUtils;
import utility.HttpStatusCode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @ControllerMapping(method = HttpMethodType.POST, path = "/user/create")
    public HttpResponse createUser(HttpRequest request) throws IOException {
        Map<String, String> header = new HashMap<>();
        HttpStatusCode status = HttpStatusCode.NOT_FOUND;
        String contentType = "text/html";

        logger.debug("user - createUser");
        Map<String, String> params = null;

        if(!request.getBody().isBlank())
            params = HttpRequestUtils.parseQueryString(request.getBody());

        Objects.requireNonNull(params);

        User user = User.UserBuilder.builder()
                .setUserId(params.get("userId"))
                .setPassword(params.get("password"))
                .setName(params.get("name"))
                .setEmail(params.get("email")).build();

        String path = "";
        status = HttpStatusCode.FOUND;
        header.put("Location", "/index.html");

        try {
            UserService.signUpUser(user);
        } catch (IllegalStateException e) {
            logger.error("/user/create - " + e.getMessage());
            header.put("Location", "/user/form.html");
        }

        logger.debug("created User: {}", UserDatabase.findUserById(params.get("userId")));

        if(!path.isBlank())
            contentType = Files.probeContentType(Path.of(path));
        logger.debug("contentType: " +contentType);

        return new HttpResponse(path, status, contentType, header);
    }

    @ControllerMapping(method = HttpMethodType.POST, path = "/user/login")
    public HttpResponse loginUser(HttpRequest request) throws IOException {
        Map<String, String> header = new HashMap<>();
        HttpStatusCode status = HttpStatusCode.NOT_FOUND;
        String contentType = "text/html";

        boolean loginSuccess = false;
        Map<String, String> params = null;

        if(!request.getBody().isBlank())
            params = HttpRequestUtils.parseQueryString(request.getBody());

        Objects.requireNonNull(params);

        String path = "";
        status = HttpStatusCode.FOUND;

        try {
            loginSuccess = UserService.loginUser(params.get("userId"), params.get("password"));
            header.put("Location", "/index.html");

        } catch (IllegalStateException | IllegalArgumentException e) {
            logger.error("/user/login - " + e.getMessage());
            header.put("Location", "/user/login_failed.html");
        }

        if(loginSuccess) {
            // Session을 Cookie로 저장
            SessionCookie session = SessionService.createSession(params.get("userId"), params.get("name"), Map.of());
            header.put("Set-Cookie", session.toString());
            logger.debug("/user/login - " + session);
        }

        logger.debug("Login User: {}", params.get("userId"));

        if(!path.isBlank())
            contentType = Files.probeContentType(Path.of(path));
        logger.debug("contentType: " +contentType);

        return new HttpResponse(path, status, contentType, header);
    }
}
