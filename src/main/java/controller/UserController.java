package controller;

import db.Database;
import model.User;
import request.Request;
import response.Response;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class UserController implements Controller {
    private static final int USERID_INDEX = 0;
    private static final int USERPWD_INDEX = 1;
    private static final int USERNAME_INDEX = 2;
    private static final int USEREMAIL_INDEX = 3;

    @Override
    public void selectedController(Request request, Response response) throws IOException {
        System.out.println("firstLine : " + request.getRequestLine().getURL());
        String requestURL = request.getRequestLine().getURL();
        List<String> userInfos = parseUrlToGetUserInfo(requestURL);
        User user = new User(userInfos.get(USERID_INDEX), userInfos.get(USERPWD_INDEX),
                userInfos.get(USERNAME_INDEX), userInfos.get(USEREMAIL_INDEX));

        Database.addUser(user);
        byte[] body = Files.readAllBytes(new File("./src/main/resources/templates" + "/index.html").toPath());
        response.response302Header();
        response.responseBodyTemp();
    }

    public List<String> parseUrlToGetUserInfo(String requestURL) {
        String result;
        result = requestURL.split("\\?")[1];
        String[] unParsedUserInfos;
        List<String> parsedUserInfo = new ArrayList<>();
        unParsedUserInfos = result.split("&");
        for (String eachInfo : unParsedUserInfos) {
            parsedUserInfo.add(eachInfo.split("=")[1]);
        }
        return parsedUserInfo;
    }
}
