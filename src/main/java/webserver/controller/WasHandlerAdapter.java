package webserver.controller;

import model.request.Request;
import model.response.Response;

public interface WasHandlerAdapter {
    Response process(Request request);
}
