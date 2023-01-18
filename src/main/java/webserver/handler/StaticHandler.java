package webserver.handler;


import db.SessionStorage;
import util.HttpParser;
import was.view.ViewResolver;
import webserver.domain.HttpRequest;
import webserver.domain.HttpResponse;
import webserver.domain.HttpResponseMessage;
import webserver.session.Session;

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


        if (httpRequest.isContainsCookie() && uri.contains(".html")) {
            UUID sessionId = httpRequest.getSessionId().get();
            return new HttpResponseMessage(httpResponse.forward(path, sessionId), httpResponse.getBody());
        }
        return new HttpResponseMessage(httpResponse.forward(path), httpResponse.getBody());
    }
}
