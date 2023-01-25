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
    public HttpResponseMessage handle(HttpRequest httpRequest) {
        String uri = httpRequest.getRequestLine().getUrl();
        HttpResponse httpResponse = new HttpResponse();
        String path = httpResponse.findPath(uri);
        UUID sessionId = httpRequest.getSessionId().orElse(null);
        if (SessionStorage.isExist(sessionId) && uri.contains(".html")) {
            return new HttpResponseMessage(httpResponse.forward(path, sessionId), httpResponse.getBody());
        }
        return new HttpResponseMessage(httpResponse.forward(path), httpResponse.getBody());
    }
}
