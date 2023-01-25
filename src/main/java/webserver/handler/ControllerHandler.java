package webserver.handler;

import webserver.domain.HttpRequest;
import webserver.domain.HttpResponse;

public interface ControllerHandler {
    public HttpResponse handle(HttpRequest httpRequest);
}
