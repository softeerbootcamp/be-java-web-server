package controller;

import Utility.UserValidation;
import httpMock.CustomHttpFactory;
import httpMock.CustomHttpRequest;
import httpMock.CustomHttpResponse;
import httpMock.constants.HttpMethod;
import model.Session;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.SessionService;
import service.UserService;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class UserAccountController implements RequestController {
    private static Logger logger = LoggerFactory.getLogger(UserAccountController.class);

    static UserAccountController userAccountService;

    private final Map<String, RequestController> routingTable = new HashMap<>() {{
        put("/user/create", (req) -> makeAccount(req));
        put("/user/login", (req) -> loginAccount(req));
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
        return CustomHttpFactory.NOT_FOUND();
    }

    public CustomHttpResponse makeAccount(CustomHttpRequest req) {
        Set<HttpMethod> allowedMethods = Set.of(HttpMethod.POST);
        if (!allowedMethods.contains(req.getHttpMethod()))
            return CustomHttpFactory.METHOD_NOT_ALLOWED();

        Map<String, String> bodyParams = req.parseBodyFromUrlEncoded();
        String userId = bodyParams.get("userId");
        String password = bodyParams.get("password");
        String name = bodyParams.get("name");
        String email = bodyParams.get("email");

        if (UserService.findUserById(userId) != null)
            return CustomHttpFactory.BAD_REQUEST("userID duplicated");

        if (!UserValidation.isEmailValid(email))
            return CustomHttpFactory.BAD_REQUEST("email type invalid");

        if (!UserValidation.isPasswordValid(password))
            return CustomHttpFactory.BAD_REQUEST("password type invalid");

        UserService.addUser(new User(userId, password, name, email));

        return CustomHttpFactory.REDIRECT("/index.html");
    }

    public CustomHttpResponse loginAccount(CustomHttpRequest req) {
        Set<HttpMethod> allowedMethods = Set.of(HttpMethod.POST);
        if (!allowedMethods.contains(req.getHttpMethod()))
            return CustomHttpFactory.METHOD_NOT_ALLOWED();

        Map<String, String> bodyParams = req.parseBodyFromUrlEncoded();
        String userId = bodyParams.get("userId");
        String password = bodyParams.get("password");

        User customer = UserService.findUserById(userId);
        if (customer != null && customer.getPassword().equals(password)) {
            logger.debug("User {} login success", customer.getName());
            CustomHttpResponse res = CustomHttpFactory.REDIRECT("/index.html");
            Session sess = SessionService.addUserToSession(customer);
            res.addToCookie(sess.toString());
            return res;
        }

        logger.debug("User login failed");
        return CustomHttpFactory.REDIRECT("/user/login_failed.html");
    }
}
