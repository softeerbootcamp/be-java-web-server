package service;

import db.Database;
import dto.UserInfoDTO;
import model.User;

public class UserService {
    public void signIn(UserInfoDTO userInfo) {
        User findUser = Database.findUserById(userInfo.getUserId());

        if (findUser != null) {
            return;
        }

        Database.addUser(User.of(userInfo));
    }
}
