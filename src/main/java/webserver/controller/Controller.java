package webserver.controller;

import webserver.httpUtils.Request;
import webserver.httpUtils.Response;
import webserver.httpUtils.ResponseHandler;

import java.io.IOException;
import java.io.OutputStream;

public interface Controller {
    public void exec(Request req, Response res, OutputStream out) throws IOException;

    ResponseHandler resHandler = null;
}
