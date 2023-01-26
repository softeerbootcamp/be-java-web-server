package controller;

import http.request.HttpRequest;
import http.response.HttpResponse;
import http.response.HttpStatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.UserService;

import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Map;

public class UserCreateController extends AbstractController {
    public static final String INDEX_PATH = "/index.html";
    public static final String USER_FORM_PATH = "/user/form.html";
    private static final Logger logger = LoggerFactory.getLogger(UserCreateController.class);
    private final UserService userService;

    public UserCreateController(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        Map<String, String> queryParams = httpRequest.getParameters();
        logger.debug("query params: {}", queryParams);
        try {
            userService.addUser(queryParams);
            logger.info("Create User Success");
            httpResponse.sendRedirect(HttpStatusCode.FOUND, INDEX_PATH);

        } catch (SQLIntegrityConstraintViolationException e) {
            e.printStackTrace();
            httpResponse.sendRedirect(HttpStatusCode.FOUND, USER_FORM_PATH);
        }
    }
}
