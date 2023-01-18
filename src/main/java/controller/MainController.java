package controller;

import db.Database;
import filesystem.FileSystem;
import filesystem.FindResource;
import http.common.Cookie;
import http.common.Session;
import http.request.HttpRequest;
import http.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static filesystem.PathResolver.dynamicHtmls;

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
        if (isAuthenticated(request) && dynamicHtmls.contains(request.getUrl())) {
            return FileSystem.findDynamicResource(request);
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
