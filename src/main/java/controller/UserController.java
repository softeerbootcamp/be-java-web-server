package controller;

import http.request.HttpRequest;
import http.request.HttpUri;
import http.request.RequestLine;
import http.response.HttpResponse;
import http.response.HttpResponseFactory;
import service.UserService;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public class UserController implements Controller {

    private static final String path = "user";
    private static final UserService userService = new UserService();

    @Override
    public HttpResponse doService(HttpRequest httpRequest) throws Exception {
        RequestLine requestLine = httpRequest.getRequestLine();
        HttpUri httpUri = requestLine.getHttpUri();
        Method[] methods = UserService.class.getMethods();
        for (Method method : methods) {
            if(httpUri.getPath().contains(method.getName())) {
                return (HttpResponse) method.invoke(userService, httpRequest);
            }
        }
        return HttpResponseFactory.NOT_FOUND("Method Not Found");
    }

    @Override
    public boolean isMatch(HttpRequest httpRequest) {
        RequestLine requestLine = httpRequest.getRequestLine();
        HttpUri httpUri = requestLine.getHttpUri();
        return path.equals(httpUri.getDetachControllerPath());
    }
}
