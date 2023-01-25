package webserver.handler;

import webserver.domain.HttpRequest;
import webserver.domain.HttpResponse;
import webserver.domain.HttpResponseMessage;

public interface ControllerHandler {
    public HttpResponse handle(HttpRequest httpRequest);
}
