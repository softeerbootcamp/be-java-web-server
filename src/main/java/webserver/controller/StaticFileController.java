package webserver.controller;

import webserver.httpUtils.Request;
import webserver.httpUtils.Response;
import webserver.httpUtils.ResponseHandler;

import java.io.IOException;
import java.io.OutputStream;

public class StaticFileController implements Controller{
    public StaticFileController(){}

    private ResponseHandler resHandler;

    @Override
    public void exec(Request req, Response res, OutputStream out) throws IOException {
        resHandler = new ResponseHandler(res);


        resHandler.makeAndSendRes(out, req.getReqLine());
    }
}
