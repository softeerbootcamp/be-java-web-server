package parser;

import java.util.HashMap;
import java.util.Map;

public class StringParser {

    public static String urlParsing(String requestLine) {
        String[] tokens = requestLine.split(" ");
        return String.format("./templates%s", tokens[1]);
    }

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
