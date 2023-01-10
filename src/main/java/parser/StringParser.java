package parser;

import model.User;

public class StringParser {

    public String stringSplit(String line) {
        String[] token = line.split(" ");
        String indexString = token[1];
        return indexString;
    }

    public String directorySplit(String urlString){
        int directoryIdx = 0;
        for (int i = 0; i < urlString.length(); i++) {
            if(urlString.charAt(i) == '.'){
                directoryIdx = i;
            }
        }
        return urlString.substring(0,directoryIdx);
    }

    public String extensionSplit(String urlString) {
        int extensionDotIdx;
        int extensionDataIdx;
        for (int i = 0; i < urlString.length(); i++) {
            extensionDotIdx = extensionDotSplit(urlString, i);
            extensionDataIdx = extensionDataSplit(urlString, i);
            if(extensionDotIdx != 0) return urlString.substring(extensionDotIdx+1);
            if(extensionDataIdx != 0){
                String data = urlString.substring(extensionDataIdx+1);
                userDataParser(data);
                return data;
            }
        }
        return "";
    }

    public int extensionDotSplit(String urlString, int idx) {
        int getIdx = 0;
        if(urlString.charAt(idx) == '.'){
            getIdx = idx;
        }
        return getIdx;
    }

    public int extensionDataSplit(String urlString, int idx) {
        int getIdx = 0;
        if(urlString.charAt(idx) == '?'){
            getIdx = idx;
        }
        return getIdx;
    }

    public void userDataParser(String userData) {
        String[] parseUserData = userData.split("=");
        for (int i = 0; i < parseUserData.length; i++) {
            inUserData(parseUserData, i);
        }
    }

    public void inUserData(String[] inputData, int idx) {

        String userId = "";
        String password = "";
        String name = "";
        String email = "";

        if(inputData[idx].equals("userId")) userId = inputData[idx + 1];
        if(inputData[idx].equals("password")) password = inputData[idx + 1];
        if(inputData[idx].equals("name")) name = inputData[idx + 1];
        if(inputData[idx].equals("email"))  email = inputData[idx + 1];
        User user = new User(userId,password,name,email);
        user.print();
    }

    public int parsingData(String data) {
        int idx = 0;
        for (int i = 0; i < data.length(); i++) {
            if (data.charAt(i) == '&') {
                idx = i;
            }
        }
        return idx;
    }
}
