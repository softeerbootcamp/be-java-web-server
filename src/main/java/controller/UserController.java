package controller;

import http.request.HttpRequest;
import http.request.HttpUri;
import http.response.HttpResponse;
import http.response.HttpResponseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.UserService;


import java.util.Arrays;

public class UserController implements Controller {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private static final UserService userService = new UserService();

    @Override
    public HttpResponse doService(HttpRequest httpRequest) {
        HttpUri httpUri = httpRequest.getUri();
        try {
            return (HttpResponse) Arrays.stream(UserService.class.getMethods())
                    .filter(m -> httpUri.getPath().contains(m.getName()))
                    .findFirst()
                    .get()
                    .invoke(userService, httpRequest);
        } catch (Exception e) {
            logger.error(e.getCause().getMessage());
            return HttpResponseFactory.NOT_FOUND(e.getCause().getMessage());
        }
    }

}
