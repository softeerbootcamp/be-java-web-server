package util;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class Redirect {
    private static Map<String, String> redirectLink = new HashMap<>(){{
        put("/create","/index.html");
        put("/login","/index.html");
        put("/memo/create", "/index.html");
        put("/user/list.html","/user/login.html");
        put("/qna/form.html","/user/login.html");
    }};

    public static String getRedirectLink(String reqAttribute){
        return redirectLink.getOrDefault(reqAttribute,"");
    }
}
