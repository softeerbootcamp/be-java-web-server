package webserver.controller;

import webserver.httpUtils.Request;
import webserver.httpUtils.Response;
import webserver.httpUtils.ResponseHandler;
import webserver.service.LogInService;
import webserver.service.Service;
import webserver.service.SignUpService;

import java.io.IOException;
import java.io.OutputStream;

public class StaticFileController implements Controller{
    public StaticFileController(){}

    private ResponseHandler resHandler;

    @Override
    public void exec(Request req, OutputStream out) throws IOException {
        String query = req.getReqLine().getQuery();
        Service service = new Service() {};
        if(query.contains("user/create"))
        {
            service = new SignUpService();
        }
        if(query.contains("user/login"))
        {
            service = new LogInService();
        }

        Response res = service.exec(req);
        resHandler = new ResponseHandler(res);
        resHandler.sendRes(out);
    }
}
