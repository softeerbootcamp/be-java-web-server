package webserver.controller;

import customException.AlreadyHasSameIdException;
import db.Database;
import model.User;
import webserver.Paths;
import webserver.controller.Controller;
import webserver.httpUtils.Request;
import webserver.httpUtils.Response;

import java.util.HashMap;
import java.util.Map;

public class SignUpController implements Controller {
    private static final String USER_ID = "userId";
    private static final String USER_PassWord = "password";
    private static final String USER_Name = "name";
    private static final String USER_Email = "email";

    @Override
    public void exec(Request req, Response res)
    {
        Map<String, String> UserInfo = parseString(unparsedInfo);

        try{
            if(null != Database.findUserById(UserInfo.get(USER_ID)))
            // 이미 데이터베이스에 같은 이름의 아이디가 있는 경우
            {
                throw new AlreadyHasSameIdException("이미 같은 아이디의 유저가 있습니다.");
            }
            User newUser = new User(UserInfo.get(USER_ID), UserInfo.get(USER_PassWord), UserInfo.get(USER_Name), UserInfo.get(USER_Email));
            Database.addUser(newUser);
        } catch(AlreadyHasSameIdException e) {
            unparsedInfo = Paths.ENROLL_FAIL_PATH;
        }
        return unparsedInfo;
    }

    private static HashMap<String, String> parseString(String unparsedInfo)
    {
        HashMap<String, String> returnMap = new HashMap<String, String>();
        String onlyUseIndex1[] = unparsedInfo.split("\\?");
        return getUserInfoFromString(onlyUseIndex1[1]);
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
}
