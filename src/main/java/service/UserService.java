package service;

import db.Database;
import exception.UserValidationException;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public void signUp(Map<String, String> userInfo) {
        validateDuplication(userInfo.get("userId"));

        User user = createUser(userInfo);
        logger.debug("user : {}", user);

        Database.addUser(user);
    }

    public boolean login(Map<String, String> userInfo) {
        String userId = userInfo.get("userId");
        String password = userInfo.get("password");

        User findUser = Database.findUserById(userId);

        if (findUser == null) {
            return false;
        }

        return findUser.getPassword().equals(password);
    }

    private void validateDuplication(String userId) {
        if (Database.findUserById(userId) != null) {
            throw new UserValidationException("중복되는 아이디입니다.");
        }
    }

    private User createUser(Map<String, String> userInfo) {
        String userId = userInfo.get("userId");
        String password = userInfo.get("password");
        String name = userInfo.get("name");
        String email = userInfo.get("email");

        return new User(userId, password, name, email);
    }
}
