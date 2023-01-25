package controller;

import Utility.HtmlMakerUtility;
import exceptions.CustomException;
import httpMock.CustomHttpFactory;
import httpMock.CustomHttpRequest;
import httpMock.CustomHttpResponse;
import httpMock.constants.HttpMethod;
import model.Session;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import repository.MemoryUserRepo;
import service.SessionService;
import service.StaticFileService;
import service.UserService;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class UserAccountController implements RequestController {
    private static final Logger logger = LoggerFactory.getLogger(UserAccountController.class);

    private static UserAccountController userAccountController;

    private final Map<String, RequestController> routingTable = new HashMap<>() {{
        put("/user/create", (req) -> makeAccount(req));
        put("/user/login", (req) -> loginAccount(req));
        put("/user/logout", (req) -> logoutAccount(req));
        put("/user/list", (req) -> userList(req));
        put("/user/form", (req) -> userCreateForm(req));
    }};


    public static UserAccountController get() {
        if (userAccountController == null) {
            synchronized (UserAccountController.class) {
                userAccountController = new UserAccountController();
            }
        }
        return userAccountController;
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
        Set<HttpMethod> allowedMethods = Set.of(HttpMethod.POST, HttpMethod.GET);
        if (!allowedMethods.contains(req.getHttpMethod()))
            return CustomHttpFactory.METHOD_NOT_ALLOWED();

        if (req.getHttpMethod() == HttpMethod.GET)
            return StaticFileController.get().handleRequest(req);

        Map<String, String> bodyParams = req.parseBodyFromUrlEncoded();

        try {
            User user = UserService.addUser(bodyParams);
            CustomHttpResponse res = CustomHttpFactory.REDIRECT("/index.html");
            Session sess = SessionService.addUserToSession(user);
            res.addToCookie(sess.toString());
            return res;
        } catch (CustomException e) {
            return CustomHttpFactory.BAD_REQUEST(e.getMessage());
        }
    }

    public CustomHttpResponse loginAccount(CustomHttpRequest req) {
        Set<HttpMethod> allowedMethods = Set.of(HttpMethod.POST, HttpMethod.GET);
        if (!allowedMethods.contains(req.getHttpMethod()))
            return CustomHttpFactory.METHOD_NOT_ALLOWED();

        logger.info("http method is {}", req.getHttpMethod());
        if (req.getHttpMethod() == HttpMethod.GET)
            return StaticFileController.get().handleRequest(req);

        Map<String, String> bodyParams = req.parseBodyFromUrlEncoded();
        User customer = UserService.getUserByReqbody(bodyParams);

        if (customer == User.GUEST) {
            logger.debug("User login failed");
            return CustomHttpFactory.REDIRECT("/user/login_failed.html");
        }

        CustomHttpResponse res = CustomHttpFactory.REDIRECT("/index.html");
        Session sess = SessionService.addUserToSession(customer);
        res.addToCookie(sess.toString());
        return res;
    }

    public CustomHttpResponse logoutAccount(CustomHttpRequest req) {
        Set<HttpMethod> allowedMethods = Set.of(HttpMethod.POST, HttpMethod.GET);
        if (!allowedMethods.contains(req.getHttpMethod()))
            return CustomHttpFactory.METHOD_NOT_ALLOWED();

        Session expired = SessionService.expireSession(req.getSSID());
        logger.info("SID {} logout success", req.getSSID());
        CustomHttpResponse res = CustomHttpFactory.REDIRECT("/user/login.html");
        res.addToCookie(expired.toString());
        return res;
    }


    public CustomHttpResponse userList(CustomHttpRequest req) {
        Set<HttpMethod> allowedMethods = Set.of(HttpMethod.GET);
        if (!allowedMethods.contains(req.getHttpMethod()))
            return CustomHttpFactory.METHOD_NOT_ALLOWED();

        if (!SessionService.isValidSSID(req.getSSID())) {
            return CustomHttpFactory.REDIRECT("/user/login.html");
        }

        File file = StaticFileService.getFile("/user/list.html");
        try {
            User user = SessionService.getUserBySessionId(req.getSSID()).orElse(User.GUEST);
            List<User> userList = new ArrayList<>(MemoryUserRepo.getInstance().findAll());

            Map<String, String> defaultTemplate = HtmlMakerUtility.getDefaultTemplate(user.getName());
            defaultTemplate.put("userTable", HtmlMakerUtility.userListRows(userList));

            String listPageStr = StaticFileService.renderFile(file, defaultTemplate);

            return CustomHttpFactory.OK_HTML(listPageStr);
        } catch (IOException e) {
            logger.error("error while reading {}", file.getPath());
            return CustomHttpFactory.INTERNAL_ERROR("error while reading " + file.getPath());
        }
    }

    public CustomHttpResponse userCreateForm(CustomHttpRequest req) {
        return StaticFileController.get().handleRequest(req);
    }
}
