package controller;

import db.Database;
import enums.ContentTypeEnum;
import enums.ControllerTypeEnum;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.Request;
import response.ResponseFactory;
import webserver.RequestResponseHandler;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class UserController implements Controller {
    private static final int USERID_INDEX = 0;
    private static final int USERPWD_INDEX = 1;
    private static final int USERNAME_INDEX = 2;
    private static final int USEREMAIL_INDEX = 3;
    private static final Logger logger = LoggerFactory.getLogger(RequestResponseHandler.class);

    // todo: user 정보들을 parse 하는 것은 좋지만, 추후 Validation Check 등에서 불편할 수 있으므로, Map 형태로 바꾸어 보자.
    @Override
    public ResponseFactory controllerService(Request request) throws IOException {
        logger.debug("firstLine : " + request.getRequestLine().getURL());
        List<String> requestRequestBody = request.getRequestBody().getBodyLines();
        List<String> userInfos = parseUrlToGetUserInfo(requestRequestBody);

        List<String> decodedUserInfos = decoder(userInfos);
        User user = new User(decodedUserInfos.get(USERID_INDEX), decodedUserInfos.get(USERPWD_INDEX),
                decodedUserInfos.get(USERNAME_INDEX), decodedUserInfos.get(USEREMAIL_INDEX));

        Database.addUser(user);
        byte[] body = Files.readAllBytes(new File("./src/main/resources/templates" + "/index.html").toPath());
        String addedLine = "Location : /index.html";
        ResponseFactory responseFactory = new ResponseFactory.Builder()
                .setResponseStatusLine(ControllerTypeEnum.USER)
                .setResponseHeader(ContentTypeEnum.CSS, body.length)
                .addResponseHeader(addedLine)
                .setResponseBody(body)
                .build();
        return responseFactory;
    }

    public static List<String> decoder(List<String> userInfos) {
        List<String> decodedInfos = new ArrayList<>();
        for(String info : userInfos){
            decodedInfos.add(URLDecoder.decode(info, StandardCharsets.UTF_8));
        }
        return decodedInfos;
    }

    public List<String> parseUrlToGetUserInfo(List<String> requestBodyLine) {
        String UserInfos = getUserInfoFromBodyLines(requestBodyLine);
        List<String> parsedUserInfo = new ArrayList<>();
        String[] unParsedUserInfos = UserInfos.split("&");
        for (String eachInfo : unParsedUserInfos) {
            parsedUserInfo.add(eachInfo.split("=")[1]);
        }
        return parsedUserInfo;
    }

    public String getUserInfoFromBodyLines(List<String> lines) {
        for (String line : lines
        ) {
            if (line.contains("user")) return line;
        }
        return null;
    }
}
