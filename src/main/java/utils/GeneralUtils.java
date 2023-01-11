package utils;

public class GeneralUtils {
    public static String[] stringParser(String str, String seperator){
        String[] parsedStr = str.split(seperator);
        return parsedStr;
    }
}
