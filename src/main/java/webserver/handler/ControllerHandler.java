package webserver.handler;

import webserver.domain.HttpResponseMessage;

public interface ControllerHandler {
    public HttpResponseMessage handle();
}
