package controller;

import filesystem.FileSystem;
import filesystem.FindResource;
import filesystem.PathResolver;
import http.request.HttpRequest;
import http.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.AuthService;

import static filesystem.PathResolver.authenticatedUrl;
import static filesystem.PathResolver.indexRequestUrl;

public class MainController implements Controller {

    private final Logger logger = LoggerFactory.getLogger(MainController.class);
    private final AuthService authService = new AuthService();


    @Override
    public void service(HttpRequest request, HttpResponse response) {
        logger.debug("main controller called");
        FindResource resource = getResource(request);
        response.update(resource);
        response.send();
    }

    private FindResource getResource(HttpRequest request) {
        // todo: 리팩토링,,,,
        if (indexRequestUrl.contains(request.getUrl())) {
            if (authService.isAuthenticated(request)) {
                return FileSystem.getIndexPage(request);
            }
        }
        if (authenticatedUrl.contains(request.getUrl())) {
            if (authService.isAuthenticated(request)) {
                return FileSystem.getUserListPage(request);
            }
            return new FindResource(PathResolver.LOGIN_HTML, FileSystem.readFile(PathResolver.parse(PathResolver.LOGIN_HTML)));
        }
        return FileSystem.findStaticResource(request);
    }
}
