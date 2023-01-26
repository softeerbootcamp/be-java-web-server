package webserver.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.httpUtils.Request;
import webserver.httpUtils.Response;
import webserver.httpUtils.ResponseSender;
import webserver.service.AlreadyLoggedInService;
import webserver.service.ArticleListService;
import webserver.service.Service;

import java.io.IOException;
import java.io.OutputStream;

public class HomePathController implements Controller{
    private static final Logger logger = LoggerFactory.getLogger(HomePathController.class);
    public HomePathController(){}

    @Override
    public void exec(Request req, OutputStream out) throws IOException
    {
        String cookieStr;
        String sid_userid;
        Response res;

        Service service = new ArticleListService();
        res = service.exec(req);

        if(req.hasCookie())
        {
            cookieStr = req.getCookie();
            sid_userid = getSid(cookieStr);
            byte changedBody[] = res.getResBody();
            service = new AlreadyLoggedInService(sid_userid, changedBody);
            res = service.exec(req);
        }

        ResponseSender resSender = new ResponseSender(res);
        resSender.sendRes(out);
    }

    private String getSid(String unparsed)
    {
        String token[] = unparsed.split("=");
        return token[1];
    }
}
