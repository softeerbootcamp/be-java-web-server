package service;

import db.Database;
import model.User;

public class JoinService {

    private User user;
    private String userId;

    public String joinUser(String[] parseData) {
        userId = parseData[1];
        user = new User(parseData[1], parseData[3], parseData[5], parseData[7]);
        Database.addUser(user);
        return userId;
    }
}
