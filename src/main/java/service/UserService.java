package service;

import db.Database;
import model.User;

import java.util.Map;

public class UserService {
    public void addUser(Map<String, String> parsedUserMap) {
        Database.addUser(new User(parsedUserMap.get("userId"), parsedUserMap.get("password"), parsedUserMap.get("name"), parsedUserMap.get("email")));
    }
}
