package service;

import db.Database;
import model.User;

public class JoinService {

    public void userDataParser(String userData) { // age=13&name=park를 =을 기준으로 분리
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
        if(inputData[idx].equals("userId")) userId = readData(inputData[idx + 1]);
        if(inputData[idx].equals("password"))  password = readData(inputData[idx + 1]);
        if(inputData[idx].equals("name"))  name = readData(inputData[idx + 1]);
        if(inputData[idx].equals("email"))   email = readData(inputData[idx + 1]);
        User user = new User(userId,password,name,email);
        Database.addUser(user);
        System.out.println("Check Insert : " + userId);
        System.out.println("Check Insert : " + password);
        System.out.println("Check Insert : " + name);
        System.out.println("Check Insert : " + email);
    }

    public String readData(String data) {
        String userData = "";
        for (int i = 0; i < data.length(); i++) {
            userData += separateData(data.charAt(i));
        }
        return userData;
    }

    public char separateData(char ch) {  // &을 기준으로 한번 더 분리
        if( ch == '&') return ' ';
        return ch;
    }
}
