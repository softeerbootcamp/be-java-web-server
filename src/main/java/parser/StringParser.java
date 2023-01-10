package parser;

public class StringParser {

    public String stringSplit(String line) {
        String[] token = line.split(" ");
        String indexString = token[1];
        return indexString;
    }

    public String extensionSplit(String urlString) {
        int extensionIdx = 0;
        for (int i = 0; i < urlString.length(); i++) extensionIdx = extensionDotSplit(urlString, i);
        return urlString.substring(extensionIdx+1);
    }

    public int extensionDotSplit(String urlString, int idx) {
        int getIdx = 0;
        if(urlString.charAt(idx) == '.'){
            getIdx = idx;
        }
        return getIdx;
    }

}
