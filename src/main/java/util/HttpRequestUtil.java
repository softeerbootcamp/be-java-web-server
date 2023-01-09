package util;

import java.util.HashMap;
import java.util.Map;

public class HttpRequestUtil {
    public static Map<String,String> parseQueryString(String reqURL){
        Map<String,String> info = new HashMap<>();
        try{
            String reqInfo = reqURL.split("\\?")[1];
            String[] infoSet = reqInfo.split("&");
            for (String str: infoSet){
                String[] kv = str.split("=");
                info.put(kv[0],kv[1]);
            }
            for (String str: infoSet)
                System.out.println(str);
            return info;
        }catch (ArrayIndexOutOfBoundsException e){
            return info;
        }
    }

    public static String getOnlyURL(String reqURL){
        try {
            return reqURL.substring(0,reqURL.indexOf("?"));
        } catch (StringIndexOutOfBoundsException e){
            return reqURL;
        }
    }

    public static String getFileExtension(String reqURL){
        return reqURL.substring(reqURL.lastIndexOf('.')+1);
    }

}