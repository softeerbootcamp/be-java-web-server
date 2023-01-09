package webserver.utils;

import java.util.HashMap;
import java.util.Map;

public class RequestUtil {

    public static String[] parseRequestedLine(String req){
        String [] parsedReq = req.split(" ");
        return parsedReq;
    }

    public static String[] parseRequestedHeader(String req){
        String [] parsedReq = req.split(" ", 2);
        return parsedReq;
    }

    public static void readHttpRequest(Map<String, String> map){
        for (String key: map.keySet()){
            System.out.println(key+ " = " + map.get(key));
        }
    }


}
