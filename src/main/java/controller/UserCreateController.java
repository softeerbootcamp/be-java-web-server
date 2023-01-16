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

    private static final Logger logger = LoggerFactory.getLogger(UserCreateController.class);

    public UserCreateController() {
        this.paths = Collections.singletonList("/user/create");
    }

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        Uri uri = httpRequest.getUri();
        Map<String, String> queryParams = uri.getParameters();
        UserService.create(queryParams);

        httpResponse.sendRedirect("/index.html");
    }


}
