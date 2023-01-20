package Controller;

import Request.HttpRequest;
import Request.StatusCode;
import Response.HttpResponse;
import Response.HttpResponseBody;
import Response.HttpResponseHeaders;
import Response.HttpResponseStartLine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import view.UserListView;

import java.util.Objects;

public class UserListController implements AuthController {
    public static final String PATH = "/user/list";
    private static final Logger logger = LoggerFactory.getLogger(UserListController.class);
    private static UserListController userListController = null;
    public static UserListController getInstance(){
        if(Objects.isNull(userListController)){
            userListController = new UserListController();
        }
        return userListController;
    }
    @Override
    public HttpResponse createResponse(HttpRequest httpRequest) {
        logger.debug("select UserlistController");
        byte[] body = UserListView.getInstance().render(httpRequest);
        HttpResponse response = new HttpResponse().startLine(new HttpResponseStartLine(StatusCode.OK, httpRequest.getProtocol()))
                .headers(new HttpResponseHeaders("/user/list.html", body.length))
                .body(new HttpResponseBody(body));
        return response;
    }
}
