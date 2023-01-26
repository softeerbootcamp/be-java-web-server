package webserver.controller;

import db.mysql.DB_Users;
import db.UserIdSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.httpUtils.Request;
import webserver.httpUtils.Response;
import webserver.httpUtils.ResponseSender;
import webserver.service.*;

import java.io.IOException;
import java.io.OutputStream;

public class DynamicFileController implements Controller{
    private static final Logger logger = LoggerFactory.getLogger(DynamicFileController.class);

    private ResponseSender resSender;
    @Override
    public void exec(Request req, OutputStream out) throws IOException {
        String cookieStr = new String();
        String sid_userid = new String();
        if(req.hasCookie())
        {
            cookieStr = req.getCookie();
            sid_userid = getSid(cookieStr);
        }

        Service service = new Service() {};

        // 로그인 부분을 현재 사용자 이름으로 변경하는 서비스
        service = new AlreadyLoggedInService(sid_userid);

        String query = req.getReqLine().getQuery();
        if(query.endsWith("user/list.html"))
        {
            service = new UserListService(sid_userid);
        }
        if(query.endsWith("user/create/article"))
        {
            service = new PostNewArticleService(sid_userid);
        }
        if(query.endsWith("index.html"))
        {
            service = new ArticleListService();
        }

        Response res = service.exec(req);

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
