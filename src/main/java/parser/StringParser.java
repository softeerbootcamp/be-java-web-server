package parser;

import java.util.HashMap;
import java.util.Map;

public class StringParser {


    public static Map<String,String> dataParsing(String line) {
        Map<String, String> map = new HashMap<>();
        String[] userInfo = line.split("&");
        for (int i=1; i<userInfo.length; i++) {
            System.out.println("userInfo : "+userInfo[i]);

            String[] userValues = userInfo[i].split("=");
            for (String j : userValues) System.out.println("userValue : " + j);
//            map.put(userValues[0],userValues[1]);
        }
        return map;
    }

    public String[] stringSplit(String line) {
        return line.split(" ");
    }

    public String directorySplit(String urlString){
        try{
            int idx = urlString.lastIndexOf(".");
            return urlString.substring(0,idx);
        } catch (StringIndexOutOfBoundsException e){

        }
        return "";
    }

    public String extensionSplit(String urlString) {
        try{
            int idx = urlString.lastIndexOf(".");
            return urlString.substring(idx+1);
        } catch (StringIndexOutOfBoundsException e){

        }
        return "";
    }
}
