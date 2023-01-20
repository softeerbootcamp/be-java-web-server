package util;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MessageParser {
    public static Map<String,String> parseQueryString(String queryString){
        Map<String,String> info = new HashMap<>();
        try{
            String[] infoSet = queryString.split("&");
            info = Stream.of(infoSet).map(s->s.split("=")).collect(Collectors.toMap(a->a[0],a->a[1]));
        }catch (ArrayIndexOutOfBoundsException e){
        }
        return info;
    }


    public static Map<String,String> parseKeyValue(Map<String,String> header, String headerMessage){
        String[] keyValue = headerMessage.split(":");
        header.put(keyValue[0],keyValue[1].substring(1));
        return header;
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

    public static String getRequestAttribute(String onlyURL){
        try{
            return (onlyURL.substring(onlyURL.lastIndexOf("/")));
        } catch (StringIndexOutOfBoundsException e){
            return "";
        }
    }

    public static Map<String, String> parseCookie(String cookieMsg){
        Map<String, String> cookies = new HashMap<>();
        if (cookieMsg == null) return cookies;
        try{
            String[] cookieSet = cookieMsg.split("; ");
            cookies = Stream.of(cookieSet).map(s->s.split("=")).collect(Collectors.toMap(a->a[0],a->a[1]));
        }catch (ArrayIndexOutOfBoundsException e){
        }
        return cookies;
    }

}
