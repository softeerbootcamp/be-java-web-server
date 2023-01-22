package service;

import controller.ResourceController;
import db.Database;
import http.HttpSession;
import http.SessionHandler;
import http.request.HttpMethod;
import http.request.HttpRequest;
import http.request.ResourceType;
import http.response.DynamicResolver;
import http.response.HttpResponse;
import http.response.HttpResponseFactory;
import model.User;
import util.FileIoUtil;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class UserService {

    public HttpResponse create(HttpRequest httpRequest) throws IOException {
        if(httpRequest.getHttpMethod().equals(HttpMethod.GET)) {
            return ResourceController.getInstance().doService(httpRequest);
        }
        Database.addUser(User.from(httpRequest.getRequestBody()));
        return HttpResponseFactory.FOUND("/index.html");
    }

    public HttpResponse login(HttpRequest httpRequest) throws IOException {
        if(httpRequest.getHttpMethod().equals(HttpMethod.GET)) {
            return ResourceController.getInstance().doService(httpRequest);
        }

        String requestId = httpRequest.getRequestBody().get("userId");
        String requestPassword = httpRequest.getRequestBody().get("password");

        if (isExistUser(requestId, requestPassword)) {
            User user = Database.findUserById(requestId);
            HttpSession httpSession = SessionHandler.createSession(user);

            HttpResponse httpResponse = HttpResponseFactory.FOUND("/index.html");
            httpResponse.addHeader("Set-Cookie", "sid=" + httpSession.getSid() + "; Path=/");
            return httpResponse;
        }

        return HttpResponseFactory.FOUND("/user/login_failed.html");
    }

    public HttpResponse list(HttpRequest httpRequest) throws IOException {
        Map<String, String> headers = new HashMap<>();
        if (SessionHandler.validateSession(httpRequest.getSid())) {
            HttpSession httpSession = SessionHandler.getSession(httpRequest.getSid());

            Collection<User> users = Database.findAll();
            File file = FileIoUtil.getFile(ResourceType.HTML, "/user/list.html");
            byte[] body = DynamicResolver.showUserList(file, users, httpSession.getUserName());

            return HttpResponseFactory.OK("text/html", headers, body);
        }

        return HttpResponseFactory.FOUND("/user/login.html");
    }

    public HttpResponse logout(HttpRequest httpRequest) {
        if (SessionHandler.validateSession(httpRequest.getSid())) {
            HttpSession httpSession = SessionHandler.getSession(httpRequest.getSid());
            httpSession.expire();
            return HttpResponseFactory.FOUND("/index.html");
        }
        return HttpResponseFactory.FOUND("/user/login.html");
    }

    private boolean isExistUser(String id, String pw) {
        User user = Database.findUserById(id);
        return user != null && user.getPassword().equals(pw);
    }
}
