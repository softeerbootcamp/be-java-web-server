package webserver;

import db.Database;
import model.User;

import java.util.ArrayList;
import java.util.List;

public class SignUpController {
    public static void enrollNewUser(String unparsedInfo)
    {
        List<String> userInfo = parseString(unparsedInfo);

        User newUser = new User(userInfo.get(0), userInfo.get(1), userInfo.get(2), userInfo.get(3));
        Database.addUser(newUser);
        System.out.println(Database.findAll());
    }

    private static List<String> parseString(String unparsedInfo)
    {
        String onlyUseIndex1[] = unparsedInfo.split("\\?");
        List<String> userInfo = getUserInfoFromString(onlyUseIndex1[1]);
        return userInfo;
    }

    private static List<String> getUserInfoFromString(String str)
    {
        String token[] = str.split("&");
        List<String> userInfo = new ArrayList<String>();
        for(int i = 0; i < token.length; i++)
        {
            userInfo.add(token[i].split("=")[1]);
        }
        return userInfo;
    }


    public static String redirectToIndex()
    {
        return new String("/index.html");
    }
}
