package utils;

public class Utilites {

    public static String[] stringParser(String str, String seperator){
        String[] parsedStr = str.split(seperator);
        return parsedStr;
    }
}
