package webserver.controller;

import webserver.httpUtils.Request;
import webserver.httpUtils.Response;
import webserver.httpUtils.ResponseSender;
import webserver.service.AlreadyLoggedInService;
import webserver.service.LogInService;
import webserver.service.Service;

import java.io.IOException;
import java.io.OutputStream;

public class DynamicFileController implements Controller{

    private ResponseSender resSender;
    @Override
    public void exec(Request req, OutputStream out) throws IOException {
        String cookieStr = req.getCookie();
        String sid_userid = getSid(cookieStr);

        Service service = new Service() {};
        service = new AlreadyLoggedInService(sid_userid);
        Response res = service.exec(req);

        resSender = new ResponseSender(res);
        resSender.sendRes(out);
    }

    private String getSid(String unparsed)
    {
        String token[] = unparsed.split("=");
        return token[1];
    }
}
