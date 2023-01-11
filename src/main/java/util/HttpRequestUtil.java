package util;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HttpRequestUtil {
    public static Map<String,String> parseQueryString(String reqURLParams){
        Map<String,String> info = new HashMap<>();
        try{
            String[] infoSet = reqURLParams.split("&");
            info = Stream.of(infoSet).map(s->s.split("=")).collect(Collectors.toMap(a->a[0],a->a[1]));
        }catch (ArrayIndexOutOfBoundsException e){
        }
        return info;
    }

    public static String getOnlyURL(String reqURL){
        try {
            return reqURL.substring(0,reqURL.indexOf("?"));
        } catch (StringIndexOutOfBoundsException e){
            return reqURL;
        }
    }

    public static String getURLParams(String reqURL) {
        try {
            return (reqURL.substring(reqURL.indexOf("?"))).substring(1);
        } catch (StringIndexOutOfBoundsException e) {
            return "";
        }
    }

    public static String getRequestAttribute(String reqURL){
        try{
            return (reqURL.substring(reqURL.lastIndexOf("/")));
        } catch (StringIndexOutOfBoundsException e){
            return "";
        }
    }

}
