package controller;

import db.Database;
import model.User;
import httpMock.CustomHttpRequest;
import httpMock.CustomHttpResponse;
import httpMock.constants.ContentType;
import httpMock.constants.StatusCode;

import java.util.HashMap;
import java.util.Map;

public class UserAccountController implements RequestController {
    static UserAccountController userAccountService;

    private final Map<String, RequestController> routingTable = new HashMap<>() {{
        put("/user/create", (req, res) -> makeAccount(req, res));
    }};


    public static UserAccountController get() {
        if (userAccountService == null) {
            userAccountService = new UserAccountController();
        }
        return userAccountService;
    }

    @Override
    public void handleRequest(CustomHttpRequest req, CustomHttpResponse res) {
        for (String path : routingTable.keySet()) {
            if (req.getUrl().startsWith(path)) {
                routingTable.get(path).handleRequest(req, res);
                return;
            }
        }
        RequestController.NOT_FOUND(res);
    }

    public void makeAccount(CustomHttpRequest req, CustomHttpResponse res) {
        String userId = req.getRequestParams().get("userId");
        String password = req.getRequestParams().get("password");
        String name = req.getRequestParams().get("name");
        String email = req.getRequestParams().get("email");

        Database.addUser(new User(userId, password, name, email));

        res.setStatusCode(StatusCode.FOUND);
        res.setContentType(ContentType.TEXT_PLAIN);
        res.addHeader("Location", "/index.html");
    }
}
