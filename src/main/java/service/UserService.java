package service;

import db.Database;
import dto.UserInfoDTO;
import model.User;

public class UserService {
    public String signIn(UserInfoDTO userInfo) {
        User findUser = Database.findUserById(userInfo.getUserId());

        if (findUser != null) {
            return null;
        }
        User user = User.of(userInfo);
        Database.addUser(user);

        return user.getUserId();
    }
}
