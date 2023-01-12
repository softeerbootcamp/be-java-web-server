package controller;

import http.request.HttpRequest;
import http.response.HttpResponse;
import http.request.Uri;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.UserService;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class UserCreateController implements Controller {

    private static final Logger logger = LoggerFactory.getLogger(UserCreateController.class);

    private final List<String> paths;

    public UserCreateController() {
        this.paths = Collections.singletonList("/user/create");
    }
    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        String httpMethod = httpRequest.getMethod().toString();
        if(httpMethod.equals("GET")) {
            doGet(httpRequest, httpResponse);
        }
    }

    @Override
    public boolean isUri(HttpRequest httpRequest) {
        Uri uri = httpRequest.getUri();
        return paths.stream().anyMatch(uri::isEndsWith);
    }

    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        Uri uri = httpRequest.getUri();
        Map<String, String> queryParams = uri.getParameters();
        UserService.create(queryParams);

        httpResponse.sendRedirect("/index.html");
    }

}
