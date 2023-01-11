package parser;

public class StringParser {

    public String[] dataParsing(String line) {
        String[] parse = line.split("=|\\?|&");
        return parse;
    }

    public String stringSplit(String line) {
        try{
            String[] token = line.split(" ");
            String indexString = token[1];
            return indexString;
        } catch (IndexOutOfBoundsException e){

        } catch (NullPointerException e) {

        }
        return "";
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
