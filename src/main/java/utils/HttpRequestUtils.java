package utils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class HttpRequestUtils {
    //  userId=user&password=66a41ad2-bda9-4c70-807c-e0e13ff04475&name=qet&email=wf%40ags.c

    public static Map<String,String> parseQueryString(String str){
        String[] queryInfos = Utilites.stringParser(str,"&"); //userId=user , password=66a41ad2-bda9-4c70-807c-e0e13ff04475

        Map<String, String> queries = new HashMap<>();
        for(String query : queryInfos ){
            String[] res = Utilites.stringParser(query,"=");
            queries.put(res[0],res[1]);
        }

        return queries;
    }

}
