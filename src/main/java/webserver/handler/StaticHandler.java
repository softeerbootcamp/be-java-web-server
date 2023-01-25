package webserver.handler;


import db.SessionStorage;
import webserver.domain.HttpRequest;
import webserver.domain.HttpResponse;
import webserver.domain.HttpResponseMessage;

import java.util.UUID;

public class StaticHandler implements ControllerHandler {

    private static StaticHandler staticHandler;

    private StaticHandler() {
    }

    public static StaticHandler getInstance() {
        if (staticHandler != null) {
            return staticHandler;
        }
        return staticHandler = new StaticHandler();
    }

    @Override
    public HttpResponse handle(HttpRequest httpRequest) {
        String uri = httpRequest.getRequestLine().getUrl();
        HttpResponse httpResponse = new HttpResponse();
        String path = httpResponse.findPath(uri);
        UUID sessionId = httpRequest.getSessionId().orElse(null);
        if (SessionStorage.isExist(sessionId) && uri.contains(".html")) {
            httpResponse.forward(path, sessionId);
            return httpResponse;
        }
        httpResponse.forward(path);
        return httpResponse;
    }
}
