package Controller;

import Request.HttpRequest;
import Request.StatusCode;
import Response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import view.UserListView;

import java.util.Objects;

public class UserListController implements AuthController {
    public static final String PATH = "/user/list";
    private static final Logger logger = LoggerFactory.getLogger(UserListController.class);
    public static final String USER_LIST_HTML = "/user/list.html";
    private static UserListController userListController = null;

    public static UserListController getInstance() {
        if (Objects.isNull(userListController)) {
            synchronized (UserListController.class) {
                userListController = new UserListController();
            }
        }
        return userListController;
    }

    @Override
    public HttpResponse createResponse(HttpRequest httpRequest) {
        logger.debug("select UserlistController");
        byte[] body = UserListView.getInstance().render(httpRequest);
        HttpResponse response = HttpResponse.createResponse(USER_LIST_HTML, StatusCode.OK, httpRequest.getProtocol());
        response.setHttpResponseBody(body);
        response.putHeader("Content-Length", String.valueOf(body.length));

        return response;
    }
}
