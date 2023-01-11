package controller;

import db.Database;
import httpMock.CustomHttpRequest;
import httpMock.CustomHttpResponse;
import httpMock.constants.ContentType;
import httpMock.constants.StatusCode;
import model.User;

import java.util.HashMap;
import java.util.Map;

public class UserAccountController implements RequestController {
    static UserAccountController userAccountService;

    private final Map<String, RequestController> routingTable = new HashMap<>() {{
        put("/user/create", (req) -> makeAccount(req));
    }};


    public static UserAccountController get() {
        if (userAccountService == null) {
            userAccountService = new UserAccountController();
        }
        return userAccountService;
    }

    @Override
    public CustomHttpResponse handleRequest(CustomHttpRequest req) {
        for (String path : routingTable.keySet()) {
            if (req.getUrl().startsWith(path)) {
                return routingTable.get(path).handleRequest(req);
            }
        }
        return RequestController.NOT_FOUND();
    }

    public CustomHttpResponse makeAccount(CustomHttpRequest req) {
        String userId = req.getRequestParams().get("userId");
        String password = req.getRequestParams().get("password");
        String name = req.getRequestParams().get("name");
        String email = req.getRequestParams().get("email");

        Database.addUser(new User(userId, password, name, email));

        CustomHttpResponse res = new CustomHttpResponse();
        res.setStatusCode(StatusCode.FOUND);
        res.setContentType(ContentType.TEXT_PLAIN);
        res.addHeader("Location", "/index.html");

        return res;
    }
}
