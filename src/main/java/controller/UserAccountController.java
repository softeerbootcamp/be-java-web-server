package service;

import db.Database;
import model.User;
import webserver.RequestService;
import webserver.httpMock.CustomHttpRequest;
import webserver.httpMock.CustomHttpResponse;
import webserver.httpMock.constants.ContentType;
import webserver.httpMock.constants.StatusCode;

import java.util.HashMap;
import java.util.Map;

public class UserAccountService implements RequestService {
    static UserAccountService userAccountService;

    private final Map<String, RequestService> routingTable = new HashMap<>() {{
        put("(/user/create).*", (req, res) -> makeAccount(req, res));
    }};


    public static UserAccountService get() {
        if (userAccountService == null) {
            userAccountService = new UserAccountService();
        }
        return userAccountService;
    }

    @Override
    public void handleRequest(CustomHttpRequest req, CustomHttpResponse res) {
        for (String regex : routingTable.keySet()) {
            if (req.getUrl().matches(regex)) {
                routingTable.get(regex).handleRequest(req, res);
                return;
            }
        }
        RequestService.NOT_FOUND(res);
    }

    public void makeAccount(CustomHttpRequest req, CustomHttpResponse res) {
        String userId = req.getRequestParams().get("userId");
        String password = req.getRequestParams().get("password");
        String name = req.getRequestParams().get("name");
        String email = req.getRequestParams().get("email");

        Database.addUser(new User(userId, password, name, email));

        res.setStatusCode(StatusCode.CREATED);
        res.setContentType(ContentType.TEXT_PLAIN);
        res.addToBody(("User " + userId + " created").getBytes());
    }
}
