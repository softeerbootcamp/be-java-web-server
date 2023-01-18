package controller;

import db.Database;
import filesystem.FileSystem;
import filesystem.FindResource;
import filesystem.PathResolver;
import http.common.Cookie;
import http.common.Session;
import http.request.HttpRequest;
import http.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static filesystem.PathResolver.authenticatedUrl;
import static filesystem.PathResolver.indexRequestUrl;

public class MainController implements Controller {

    private final Logger logger = LoggerFactory.getLogger(MainController.class);


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
            if (isAuthenticated(request)) {
                return FileSystem.getIndexPage(request);
            }
            return FileSystem.findStaticResource(request);
        }
        if (authenticatedUrl.contains(request.getUrl())) {
            if (isAuthenticated(request)) {
                return FileSystem.getUserListPage(request);
            }
            return new FindResource(PathResolver.LOGIN_HTML, FileSystem.readFile(PathResolver.parse(PathResolver.LOGIN_HTML)));
        }
        return FileSystem.findStaticResource(request);
    }

    private boolean isAuthenticated(HttpRequest request) {
        Cookie sessionCookie = request.getCookie(Session.SESSION_FIELD_NAME);
        if (sessionCookie == null) {
            return false;
        }
        Session session = Database.getSession(sessionCookie.getValue());
        if (session == null) {
            return false;
        }
        return session.isValid();
    }
}
