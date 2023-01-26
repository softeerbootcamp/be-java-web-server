package webserver.controller;

import webserver.httpUtils.Request;
import webserver.httpUtils.ResponseSender;

import java.io.IOException;
import java.io.OutputStream;

public interface Controller {
    public void exec(Request req, OutputStream out) throws IOException;
}
