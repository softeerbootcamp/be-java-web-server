package service;

import db.Database;
import dto.UserInfoDTO;
import model.User;

public class UserService {
    public void signIn(UserInfoDTO userInfo) {
        User newUser = new User(
                userInfo.getUserId(),
                userInfo.getPassword(),
                userInfo.getName(),
                userInfo.getEmail()
        );
        Database.addUser(newUser);
    }
}
