package webserver.controller;

import webserver.domain.HttpResponseMessage;

public interface ControllerHandler {
    public HttpResponseMessage handle();
}
