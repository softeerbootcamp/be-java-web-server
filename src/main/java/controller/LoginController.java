package controller;

import db.Database;
import enums.ContentTypeEnum;
import enums.ControllerTypeEnum;
import enums.HeaderReferenceEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.Request;
import response.NewResponse;
import response.Response;
import response.ResponseSender;
import session.HttpSessions;
import webserver.RequestResponseHandler;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import static session.HttpSessions.httpSessions;

public class LoginController implements Controller{
    private static final Logger logger = LoggerFactory.getLogger(RequestResponseHandler.class);
    private static final int USERINFO_INDEX=0;
    private static final int USERID_INDEX = 0;
    private static final int USERPWD_INDEX = 1;
    @Override
    public NewResponse controllerService(Request request) throws IOException {
        logger.debug("login request : "+request.getRequestLine().getMETHOD());
        logger.debug("login request : "+request.getRequestLine().getURL());
        logger.debug("login request : "+request.getRequestLine().getVERSION());
        logger.debug("login request : "+request.getRequestBody().getBodyLines());
        List<String> bodyLine = request.getRequestBody().getBodyLines();
        List<String> userInfos = parseUrlToGetUserInfo(bodyLine);
        byte[] body = Files.readAllBytes(new File("./src/main/resources/templates" + "/index.html").toPath());
        // 로그인이 성공하면? session add
        String addedLine="";
        if(checkIdPwdIsInDatabase(userInfos.get(USERID_INDEX),userInfos.get(USERPWD_INDEX))){
            HttpSessions.addHttpSession(userInfos.get(USERID_INDEX));
            //response 에 추가.
            addedLine+=HeaderReferenceEnum.SET_COOKIE.getValueWithSpace()+
                    "sid="+
                    httpSessions.get(userInfos.get(USERID_INDEX))+
                    "; Path=/";
        }else{
            logger.debug("login failed!");
        }
        NewResponse newResponse = new NewResponse.Builder()
                .setResponseStatusLine(ControllerTypeEnum.LOGIN)
                .setResponseHeader(ContentTypeEnum.CSS,body.length)
                .addResponseHeader(addedLine)
                .setResponseBody(body)
                .build();
        return newResponse;

    }
    public boolean checkIdPwdIsInDatabase(String id, String pwd){
        if(Database.findUserById(id).equals(null)){
            return false;
        }
        if(Database.findUserById(id).getPassword().equals(pwd)){
            return true;
        }else return false;
    }
    // todo : 해당 메소드 2번 사용되는데 받는 형태도 비슷하다. 어디 묶어볼까?
    public List<String> parseUrlToGetUserInfo(List<String> requestBodyLine) {
        String result = getUserInfoFromBodyLines(requestBodyLine);
        List<String> parsedUserInfo = new ArrayList<>();
        String[] unParsedUserInfos = result.split("&");
        for (String eachInfo : unParsedUserInfos) {
            parsedUserInfo.add(eachInfo.split("=")[1]);
        }
        return parsedUserInfo;
    }
    public String getUserInfoFromBodyLines(List<String> lines){
        for (String line:lines
        ) {
            if (line.contains("user")) return line;
        }
        return null;
    }
}
