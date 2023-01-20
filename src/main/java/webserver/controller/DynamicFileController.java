package webserver.controller;

import db.Database;
import db.UserIdSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.httpUtils.Request;
import webserver.httpUtils.Response;
import webserver.httpUtils.ResponseSender;
import webserver.service.AlreadyLoggedInService;
import webserver.service.LogInService;
import webserver.service.Service;
import webserver.service.ShowUserListService;

import java.io.IOException;
import java.io.OutputStream;

public class DynamicFileController implements Controller{
    private static final Logger logger = LoggerFactory.getLogger(DynamicFileController.class);

    private ResponseSender resSender;
    @Override
    public void exec(Request req, OutputStream out) throws IOException {
        String cookieStr = req.getCookie();
        String sid_userid = getSid(cookieStr);

        Service service = new Service() {};
        service = new AlreadyLoggedInService(sid_userid);

        if(req.getReqLine().getQuery().contains("user/list"))
        {
            service = new ShowUserListService(sid_userid);
        }

        Response res = service.exec(req);

        logger.debug("sid : " + sid_userid);
        logger.debug("name : " + Database.findUserById(UserIdSession.getUserId(sid_userid)).getName());

        resSender = new ResponseSender(res);
        resSender.sendRes(out);
    }

    private String getSid(String unparsed)
    {
        logger.debug(unparsed);
        String token[] = unparsed.split("=");
        return token[1];
    }
}
