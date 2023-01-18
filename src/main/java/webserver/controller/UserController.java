package webserver.controller;

import customException.AlreadyHasSameIdException;
import db.Database;
import model.User;
import webserver.Paths;
import webserver.httpUtils.Request;
import webserver.httpUtils.Response;
import webserver.httpUtils.ResponseHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class UserController implements Controller {
    private static final String USER_ID = "userId";
    private static final String USER_PASS_WORD = "password";
    private static final String USER_NAME = "name";
    private static final String USER_EMAIL = "email";

    private ResponseHandler resHandler;

    @Override
    public void exec(Request req, Response res, OutputStream out) throws IOException {
        resHandler = new ResponseHandler(res);

        Map<String, String> UserInfo;
        UserInfo = getUserInfoFromString(req.getReqBody());

        try{
            checkDuplicateID(UserInfo);
            User newUser = new User(UserInfo.get(USER_ID), UserInfo.get(USER_PASS_WORD), UserInfo.get(USER_NAME), UserInfo.get(USER_EMAIL));
            Database.addUser(newUser);
        } catch(AlreadyHasSameIdException e) {
            req.getReqLine().put(Request.QUERY, Paths.ENROLL_FAIL_PATH);
        }

        resHandler.makeAndSendRes(out, req.getReqLine());
    }

    private static HashMap<String, String> getUserInfoFromString(String str)
    {
        String token[] = str.split("&");
        HashMap<String, String> userInfo = new HashMap<String, String>();
        for(int i = 0; i < token.length; i++)
        {
            String splitToken[] = token[i].split("=");
            userInfo.put(splitToken[0], splitToken[1]);
        }
        return userInfo;
    }

    private void checkDuplicateID(Map<String, String> UserInfo) {
        if (null != Database.findUserById(UserInfo.get(USER_ID)))
        {
            throw new AlreadyHasSameIdException("이미 같은 아이디의 유저가 있습니다.");
        }
    }
}
