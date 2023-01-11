package webserver.utils;

import java.util.List;
import java.util.Map;

public class CommonUtils {

    public static void readMap(Map<String, String> map){
        for (String key: map.keySet()){
            System.out.println(key+ " = " + map.get(key));
        }
    }

    public static String[] parseRequestLine(String requestedPath){
        return requestedPath.split("\\?");
    }
    public static Map<String, String> parseQueryStrings(String queryStr){
        Map<String, String> datas = HttpRequestUtils.parseQueryString(queryStr);
        return datas;
    }

    public static boolean checkParameters(List<String> paramList, Map<String, String> queryStrs) {
        for(String key : queryStrs.keySet()){
            if(paramList.contains(queryStrs.get(key)))
                return false;
        }
        return true;
    }
}
