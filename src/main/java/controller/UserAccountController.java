package controller;

import Utility.UserValidation;
import httpMock.CustomHttpErrorFactory;
import httpMock.CustomHttpRequest;
import httpMock.CustomHttpResponse;
import httpMock.constants.ContentType;
import httpMock.constants.HttpMethod;
import httpMock.constants.StatusCode;
import model.User;
import service.UserService;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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
        return CustomHttpErrorFactory.NOT_FOUND();
    }

    public CustomHttpResponse makeAccount(CustomHttpRequest req) {
        Set<HttpMethod> allowedMethods = Set.of(HttpMethod.POST);
        if(!allowedMethods.contains(req.getHttpMethod()))
            return CustomHttpErrorFactory.METHOD_NOT_ALLOWED();

        Map<String, String> bodyParams = req.parseBodyFromUrlEncoded();
        String userId = bodyParams.get("userId");
        String password = bodyParams.get("password");
        String name = bodyParams.get("name");
        String email = bodyParams.get("email");

        if (UserService.findUserById(userId) != null)
            return CustomHttpErrorFactory.BAD_REQUEST("userID duplicated");

        if (!UserValidation.isEmailValid(email))
            return CustomHttpErrorFactory.BAD_REQUEST("email type invalid");

        if (!UserValidation.isPasswordValid(password))
            return CustomHttpErrorFactory.BAD_REQUEST("password type invalid");

        UserService.addUser(new User(userId, password, name, email));

        Map<String, String> headers = new HashMap<>();
        headers.put("Location", "/index.html");

        return CustomHttpResponse.of(StatusCode.FOUND, ContentType.TEXT_PLAIN, headers, "".getBytes());
    }
}
