package webserver;

import customException.AlreadyHasSameIdException;
import db.Database;
import model.User;

import java.util.HashMap;
import java.util.Map;

public class SignUpController {
    private static final String USER_ID = "userId";
    private static final String USER_PassWord = "password";
    private static final String USER_Name = "name";
    private static final String USER_Email = "email";

    public static void enrollNewUser(String unparsedInfo)
    {
        Map<String, String> UserInfo = parseString(unparsedInfo);

        if(null != Database.findUserById(UserInfo.get(USER_ID)))
        // 이미 데이터베이스에 같은 이름의 아이디가 있는 경우
        {
            throw new AlreadyHasSameIdException("이미 같은 아이디의 유저가 있습니다.");
        }
        // TODO 특수문자가 정상적으로 디비에 들어가도록 처리하기
        User newUser = new User(UserInfo.get(USER_ID), UserInfo.get(USER_PassWord), UserInfo.get(USER_Name), UserInfo.get(USER_Email));
        Database.addUser(newUser);
        System.out.println(Database.findAll());
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
