package Controller;

import Request.HttpRequest;
import Request.StatusCode;
import Response.HttpResponse;
import Response.HttpResponseBody;
import Response.HttpResponseHeaders;
import Response.HttpResponseStartLine;
import db.Database;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HtmlBuildUtil;

import java.util.Collection;

public class UserListController implements AuthController {
    private static final Logger logger = LoggerFactory.getLogger(UserListController.class);

    public UserListController() {
        logger.debug("select UserlistController");
    }

    @Override
    public HttpResponse createResponse(HttpRequest httpRequest) {
        String html = HtmlBuildUtil.userList(Database.findAll());
        byte[] body = html.getBytes();
        HttpResponse response = new HttpResponse().startLine(new HttpResponseStartLine(StatusCode.OK, httpRequest.getProtocol()))
                .headers(new HttpResponseHeaders("/user/list.html", body.length))
                .body(new HttpResponseBody(body));
        return response;
    }
}
