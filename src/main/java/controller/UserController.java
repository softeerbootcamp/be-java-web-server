package controller;

import http.request.HttpRequest;
import http.request.HttpUri;
import http.request.RequestLine;
import http.response.HttpResponse;
import http.response.HttpResponseFactory;
import service.UserService;

import java.util.Arrays;

public class UserController implements Controller {

    private static final UserService userService = new UserService();
    private static final String path = "user";

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
            return HttpResponseFactory.NOT_FOUND("Not Found Method");
        }
    }

    @Override
    public boolean isMatch(HttpRequest httpRequest) {
        RequestLine requestLine = httpRequest.getRequestLine();
        HttpUri httpUri = requestLine.getHttpUri();
        return path.equals(httpUri.getDetachControllerPath());
    }
}
