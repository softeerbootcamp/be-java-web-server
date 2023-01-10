package webserver;

import db.Database;
import model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SignUpController {
    private static final String USER_ID = "userId";
    private static final String USER_PassWord = "password";
    private static final String USER_Name = "name";
    private static final String USER_Email = "email";

    public static void enrollNewUser(String unparsedInfo)
    {
        Map<String, String> UserInfo = parseString(unparsedInfo);

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


    public static String redirectToIndex()
    {
        return new String("/index.html");
    }
}
