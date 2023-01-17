package webserver.controller;

import model.request.Request;
import model.response.Response;

public interface WasHandlerAdapter {

    void process(Request request, Response response);

}
