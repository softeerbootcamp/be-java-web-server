package utils;

public class Utility {

    public String stringSplit(String line) {
        String[] token = line.split(" ");
        String indexString = token[1];
        return indexString;

    }


}
