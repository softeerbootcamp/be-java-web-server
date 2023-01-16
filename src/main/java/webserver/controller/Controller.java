package webserver.controller;

import webserver.httpUtils.Request;
import webserver.httpUtils.Response;

public interface Controller {
    public void exec(Request req, Response res);

}
