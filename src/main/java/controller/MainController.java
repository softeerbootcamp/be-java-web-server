package controller;

import filesystem.FileSystem;
import filesystem.FindResource;
import http.request.HttpRequest;
import http.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.AuthService;
import service.UserService;

import java.util.List;

import static filesystem.PathResolver.DOMAIN;
import static filesystem.PathResolver.INDEX_HTML;

public class MainController implements Controller {

    public static final List<String> optionalAuthUrls = List.of("/", DOMAIN + INDEX_HTML);
    public static final List<String> mustNotAuthUrls = List.of("/user/login.html", "/user/form.html");
    public static final List<String> mustAuthUrls = List.of("/user/list.html");

    private final Logger logger = LoggerFactory.getLogger(MainController.class);
    private final AuthService authService = new AuthService();
    private final UserService userService = new UserService();


    @Override
    public void service(HttpRequest request, HttpResponse response) {
        logger.debug("main controller called");
        FindResource resource = getResource(request);
        response.update(resource);
        response.send();
    }

    private FindResource getResource(HttpRequest request) {
        // todo: 리팩토링,,,,
        if (mustNotAuthUrls.contains(request.getUrl())) {
            if (authService.isAuthenticated(request)) {
                return FileSystem.getStaticResource(INDEX_HTML);
            }
            return FileSystem.getStaticResource(request.getUrl());
        }

        if (mustAuthUrls.contains(request.getUrl())) {
            if (authService.isAuthenticated(request)) {
                return FileSystem.getPersonalizedResource(request.getUrl(), HtmlGenerator.getUserListLi(userService.getAllUser()));
            }
            return null;
        }

        if (optionalAuthUrls.contains(request.getUrl())) {
            if (authService.isAuthenticated(request)) {
                return FileSystem.getPersonalizedResource(request.getUrl(), HtmlGenerator.getUserAnchor(authService.getSession(request).getUser()));
            }
        }
        return FileSystem.getStaticResource(request.getUrl());
    }
}
