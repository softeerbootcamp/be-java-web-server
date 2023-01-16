package controller;

import http.ContentType;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.User;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import static db.Database.findUserById;
import static utils.FileIoUtils.loadFile;

public class UserLogInController extends AbstractController{

    public static final String REDIRECT_PATH = "/index.html";
    public static final String LOGIN_FAILED_PATH = "/user/login_failed.html";

    public UserLogInController() {
        this.paths = Collections.singletonList("/user/login");
    }

    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        Map<String, String> userInfo = httpRequest.getParameters();
        String requestUserId = userInfo.get("userId");
        String requestPassword = userInfo.get("password");

        if(isExistUser(requestUserId, requestPassword)) {
            httpResponse.sendRedirect(REDIRECT_PATH);
            return;
        }

        ContentType contentType = ContentType.HTML;
        String filepath = contentType.getDirectory() + LOGIN_FAILED_PATH;
        byte[] body = loadFile(filepath);

        httpResponse.forward(contentType, body);
    }

    private boolean isExistUser(String requestUserId, String requestPassword) {
        User user = findUserById(requestUserId);
        return user != null && requestPassword.equals(user.getPassword());
    }

}
