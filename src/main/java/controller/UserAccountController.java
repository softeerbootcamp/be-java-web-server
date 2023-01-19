package controller;

import Utility.HtmlMakerUtility;
import Utility.UserValidation;
import httpMock.CustomHttpFactory;
import httpMock.CustomHttpRequest;
import httpMock.CustomHttpResponse;
import httpMock.constants.ContentType;
import httpMock.constants.HttpMethod;
import httpMock.constants.StatusCode;
import model.Session;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import repository.UserRepo;
import service.SessionService;
import service.StaticFileService;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class UserAccountController implements RequestController {
    private static final Logger logger = LoggerFactory.getLogger(UserAccountController.class);

    static UserAccountController userAccountController;

    private final Map<String, RequestController> routingTable = new HashMap<>() {{
        put("/user/create", (req) -> makeAccount(req));
        put("/user/login", (req) -> loginAccount(req));
        put("/user/logout", (req) -> logoutAccount(req));
        put("/user/list", (req)-> userList(req));
    }};


    public static UserAccountController get() {
        if (userAccountController == null) {
            synchronized (UserAccountController.class){
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

        User user = new User(userId, password, name, email);
        UserRepo.addUser(user);
        CustomHttpResponse res = CustomHttpFactory.REDIRECT("/index.html");
        Session sess = SessionService.addUserToSession(user);
        res.addToCookie(sess.toString());
        return res;
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


    public CustomHttpResponse userList(CustomHttpRequest req){
        Set<HttpMethod> allowedMethods = Set.of(HttpMethod.GET);
        if (!allowedMethods.contains(req.getHttpMethod()))
            return CustomHttpFactory.METHOD_NOT_ALLOWED();

        if(!SessionService.isValidSSID(req.getSSID())){
            return CustomHttpFactory.REDIRECT("/user/login.html");
        }

        File file = StaticFileService.getFile("/user/list.html");
        try{
            List<User> userList = new ArrayList<>(UserRepo.findAll());
            String listPageStr = StaticFileService.renderFile(file, new HashMap<>() {{
                put("userTable", HtmlMakerUtility.userListRows(userList));
            }});
            return CustomHttpResponse.of(
                    StatusCode.OK,
                    ContentType.TEXT_HTML,
                    CustomHttpResponse.EMPTY_HEADER,
                    listPageStr.getBytes()
                    );

        } catch (IOException e) {
            logger.error("error while reading {}", file.getPath());
            return CustomHttpFactory.INTERNAL_ERROR("error while readling " + file.getPath());
        }
    }
}
