package controller;

import http.request.HttpRequest;
import http.response.HttpResponse;
import http.Uri;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.UserService;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

public class UserCreateController extends AbstractController {
    private final UserService userService;

    public static final String REDIRECT_PATH = "/index.html";

    private static final Logger logger = LoggerFactory.getLogger(UserCreateController.class);

    public UserCreateController(UserService userService) {
        this.paths = Collections.singletonList("/user/create");
        this.userService = userService;
    }

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        Uri uri = httpRequest.getUri();
        Map<String, String> queryParams = uri.getParameters();
        userService.create(queryParams);

        httpResponse.sendRedirect(REDIRECT_PATH);
    }

    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        Map<String, String> queryParams = httpRequest.getParameters();
        logger.debug("query params: {}", queryParams);
        userService.create(queryParams);

        logger.info("Create User Success");
        httpResponse.sendRedirect(REDIRECT_PATH);
    }

}
