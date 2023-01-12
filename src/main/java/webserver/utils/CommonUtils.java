package webserver.utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommonUtils {

    public static void readMap(Map<String, String> map){
        for (String key: map.keySet()){
            System.out.println(key+ " = " + map.get(key));
        }
    }

    public static String[] parseStringToList(String requestedPath, String delimeter){
        return requestedPath.split(delimeter);
    }

    public static Map<String, String> parseStringToMap(String req, String delimenter){
        Map<String, String> map = new HashMap<>();
        Arrays.stream(req.split(delimenter)).forEach(item->{
            String[] parsedHeader = parseStringToList(item, "\\?");
            map.put(parsedHeader[0], parsedHeader[1]);
        });
        return map;
    }

    public static String mapToStringSplitWithNewLine(Map<String, String> map){
        String result = "";
        for(String key : map.keySet()){
            result += key + ": " + map.get(key) + "\r\n";
        }
        return result;
    }

}
