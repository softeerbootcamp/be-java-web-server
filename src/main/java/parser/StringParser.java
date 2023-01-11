package parser;

public class StringParser {

    public String[] dataParsing(String line) {
        String[] parse = line.split("=|\\?|&");
        return parse;
    }

    public String stringSplit(String line) {
        String[] token = line.split(" ");
        String indexString = token[1];
        return indexString;
    }

    public String directorySplit(String urlString){
        int idx = urlString.lastIndexOf(".");
        return urlString.substring(0,idx);
    }

    public String extensionSplit(String urlString) {
        int idx = urlString.lastIndexOf(".");
        return urlString.substring(idx+1);
    }
}
