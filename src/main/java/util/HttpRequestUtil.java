package util;

import java.util.HashMap;
import java.util.Map;

public class HttpRequestUtil {
    public static Map<String,String> parseQueryString(String reqURL){
        Map<String,String> info = new HashMap<>();
        String reqInfo = reqURL.split("\\?")[1];
        String[] infoSet = reqInfo.split("&");
        for (String str: infoSet){
            String[] kv = str.split("=");
            info.put(kv[0],kv[1]);
        }
        return info;
    }

    public static String getOnlyURL(String reqURL){
        return reqURL.substring(0,reqURL.indexOf("?"));
    }

    public static String getFileExtension(String reqURL){
        return reqURL.substring(reqURL.lastIndexOf('.')+1);
    }

}