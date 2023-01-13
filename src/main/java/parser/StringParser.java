package parser;

import java.util.HashMap;
import java.util.Map;

public class StringParser {
    public static Map<String,String> dataParsing(String line) {
        Map<String, String> map = new HashMap<>();
        String[] userInfo = line.split("\\?|&");

        for (int i=1; i<userInfo.length; i++) {
            String[] userValues = userInfo[i].split("=");
            try{
                map.put(userValues[0],userValues[1]);
            } catch (ArrayIndexOutOfBoundsException e){
                map.put(userValues[0],"");
            }
        }
        return map;
    }
}
