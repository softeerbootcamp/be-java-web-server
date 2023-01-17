package controller;

import http.request.HttpRequest;
import http.request.HttpUri;
import http.request.RequestLine;
import http.response.HttpResponse;
import service.UserService;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class UserController implements Controller {

    private static final String path = "user";
    private static final UserService userService = new UserService();

    @Override
    public HttpResponse doService(HttpRequest httpRequest) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        RequestLine requestLine = httpRequest.getRequestLine();
        HttpUri httpUri = requestLine.getHttpUri();

        Method method = UserService.class.getDeclaredMethod(httpUri.getDetachServicePath(), HttpRequest.class);
        return (HttpResponse) method.invoke(userService, httpRequest);

    }

    @Override
    public boolean isMatch(HttpRequest httpRequest) {
        RequestLine requestLine = httpRequest.getRequestLine();
        HttpUri httpUri = requestLine.getHttpUri();
        return path.equals(httpUri.getDetachControllerPath());
    }
}
