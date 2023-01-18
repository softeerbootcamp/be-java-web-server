package util;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class Redirect {
    private static Map<String, String> redirectLink = new HashMap<>(){{
        put("/create","/index.html");
        put("/login","/index.html");
    }};

    public static String getRedirectLink(String reqAttribute){
        return redirectLink.getOrDefault(reqAttribute,"");
    }
}
