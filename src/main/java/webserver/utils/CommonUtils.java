package webserver.utils;

import java.net.http.HttpRequest;
import java.util.Map;

public class CommonUtils {

    public static void readMap(Map<String, String> map){
        for (String key: map.keySet()){
            System.out.println(key+ " = " + map.get(key));
        }
    }

    public static Map<String, String> parseQueryStrings(String queryStr){
        Map<String, String> datas = HttpRequestUtils.parseQueryString(queryStr);
        return datas;
    }
}
