package webserver.handler;

import webserver.domain.HttpRequest;
import webserver.domain.HttpResponseMessage;

public interface ControllerHandler {
    public HttpResponseMessage handle(HttpRequest httpRequest);
}
