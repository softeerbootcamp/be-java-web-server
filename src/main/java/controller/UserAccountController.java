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
import repository.UserRepo;
import service.SessionService;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class UserAccountController implements RequestController {
    private static final Logger logger = LoggerFactory.getLogger(UserAccountController.class);

    static UserAccountController userAccountService;

    private final Map<String, RequestController> routingTable = new HashMap<>() {{
        put("/user/create", (req) -> makeAccount(req));
        put("/user/login", (req) -> loginAccount(req));
        put("/user/logout", (req) -> logoutAccount(req));
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


        if (UserRepo.findUserById(userId) != null)
            return CustomHttpFactory.BAD_REQUEST("userID duplicated");

        if(!UserValidation.isUserCreationFormValid(userId, password, name, email))
            return CustomHttpFactory.BAD_REQUEST("email, password, name을 올바르게 입력해 주세요.");

        UserRepo.addUser(new User(userId, password, name, email));

        return CustomHttpFactory.REDIRECT("/index.html");
    }

    public CustomHttpResponse loginAccount(CustomHttpRequest req) {
        Set<HttpMethod> allowedMethods = Set.of(HttpMethod.POST);
        if (!allowedMethods.contains(req.getHttpMethod()))
            return CustomHttpFactory.METHOD_NOT_ALLOWED();

        Map<String, String> bodyParams = req.parseBodyFromUrlEncoded();
        String userId = bodyParams.get("userId");
        String password = bodyParams.get("password");

        User customer = UserRepo.findUserById(userId);
        if (customer != null && customer.getPassword().equals(password)) {
            logger.info("User {} login success", customer.getName());
            CustomHttpResponse res = CustomHttpFactory.REDIRECT("/index.html");
            Session sess = SessionService.addUserToSession(customer);
            res.addToCookie(sess.toString());
            return res;
        }

        logger.debug("User login failed");
        return CustomHttpFactory.REDIRECT("/user/login_failed.html");
    }

    public CustomHttpResponse logoutAccount(CustomHttpRequest req){
        Set<HttpMethod> allowedMethods = Set.of(HttpMethod.POST, HttpMethod.GET);
        if (!allowedMethods.contains(req.getHttpMethod()))
            return CustomHttpFactory.METHOD_NOT_ALLOWED();

        Session expired = SessionService.expireSession(req.getSSID());
        logger.info("SID {} logout success", req.getSSID());
        CustomHttpResponse res = CustomHttpFactory.REDIRECT("/user/login.html");
        res.addToCookie(expired.toString());
        return res;
    }
}
