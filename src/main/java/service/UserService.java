package service;

import controller.ResourceController;
import db.UserRepository;
import exception.CustomException;
import http.request.HttpMethod;
import http.request.HttpRequest;
import http.request.ResourceType;
import http.response.DynamicResolver;
import http.response.HttpResponse;
import http.response.HttpResponseFactory;
import http.session.HttpSession;
import http.session.SessionHandler;
import model.User;
import util.FileIoUtil;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class UserService {

    public HttpResponse create(HttpRequest httpRequest) {
        if (httpRequest.getHttpMethod().equals(HttpMethod.GET)) {
            return ResourceController.getInstance().doService(httpRequest);
        }
        String userId = httpRequest.getRequestBody().get("userId");
        if(Objects.nonNull(UserRepository.findUserById(userId))) {
            throw new CustomException("Duplicate Id");
        }
        UserRepository.addUser(User.from(httpRequest.getRequestBody()));
        return HttpResponseFactory.FOUND("/index.html");
    }

    public HttpResponse login(HttpRequest httpRequest) {
        if (httpRequest.getHttpMethod().equals(HttpMethod.GET)) {
            return ResourceController.getInstance().doService(httpRequest);
        }
        String requestId = httpRequest.getRequestBody().get("userId");
        String requestPassword = httpRequest.getRequestBody().get("password");

        if (isExistUser(requestId, requestPassword)) {
            User user = UserRepository.findUserById(requestId);
            HttpSession httpSession = SessionHandler.createSession(user);
            HttpResponse httpResponse = HttpResponseFactory.FOUND("/index.html");
            httpResponse.addHeader("Set-Cookie", "sid=" + httpSession.getSid() + "; Path=/");
            return httpResponse;
        }
        return HttpResponseFactory.FOUND("/user/login_failed.html");
    }

    public HttpResponse list(HttpRequest httpRequest) {
        Map<String, String> headers = new HashMap<>();
        if (SessionHandler.validateSession(httpRequest.getSid())) {
            HttpSession httpSession = SessionHandler.getSession(httpRequest.getSid());
            File file = FileIoUtil.getFile(ResourceType.HTML, "/user/list.html");
            byte[] body = DynamicResolver.createDynamicHtml(file, httpSession);

            return HttpResponseFactory.OK("text/html", headers, body);
        }
        return HttpResponseFactory.FOUND("/user/login.html");
    }

    public HttpResponse logout(HttpRequest httpRequest) {
        if (SessionHandler.validateSession(httpRequest.getSid())) {
            HttpSession httpSession = SessionHandler.getSession(httpRequest.getSid());
            SessionHandler.expireSession(httpSession);
            return HttpResponseFactory.FOUND("/index.html");
        }
        return HttpResponseFactory.FOUND("/user/login.html");
    }

    private boolean isExistUser(String id, String pw) {
        User user = UserRepository.findUserById(id);
        return user != null && user.getPassword().equals(pw);
    }
}
