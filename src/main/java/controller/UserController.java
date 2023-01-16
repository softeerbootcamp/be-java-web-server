package controller;

import db.Database;
import enums.ContentTypeEnum;
import enums.ControllerTypeEnum;
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
    // todo: user 정보들을 parse 하는 것은 좋지만, 추후 Validation Check 등에서 불편할 수 있으므로, Map 형태로 바꾸어 보자.
    @Override
    public void controllerService(Request request, Response response) throws IOException {
        System.out.println("firstLine : " + request.getRequestLine().getURL());
        String url = request.getRequestLine().getURL();
        List<String> userInfos = parseUrlToGetUserInfo(url);
        User user = new User(userInfos.get(USERID_INDEX), userInfos.get(USERPWD_INDEX),
                userInfos.get(USERNAME_INDEX), userInfos.get(USEREMAIL_INDEX));

        Database.addUser(user);
        byte[] body = Files.readAllBytes(new File("./src/main/resources/templates" + "/index.html").toPath());
        response.responseMaker(ControllerTypeEnum.USER, ContentTypeEnum.HTML,body.length,"/index.html");
        //response.responseLocationAdder("/index.html");
        response.responseBody(body);
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
