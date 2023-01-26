package service;

import db.UserDatabase;
import exception.UserValidationException;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Map;

public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private static final UserService instance = new UserService();

    public static UserService getInstance() {
        return instance;
    }

    private UserService() {
    }

    public void signUp(Map<String, String> userInfo) {
        validateDuplication(userInfo.get("userId"));

        User user = createUser(userInfo);
        logger.debug("user : {}", user);

        UserDatabase.addUser(user);
    }

    public User login(Map<String, String> userInfo) {
        String userId = userInfo.get("userId");
        String password = userInfo.get("password");

        User findUser = UserDatabase.findUserById(userId);

        if (findUser == null) {
            throw new UserValidationException("아이디가 존재하지 않습니다.");
        }

        if (!findUser.getPassword().equals(password)) {
            throw new UserValidationException("아이디 또는 비밀번호가 틀립니다.");
        }

        return findUser;
    }

    public User findUserById(String userId) {
        return UserDatabase.findUserById(userId);
    }

    public Collection<User> findUserList() {
        return UserDatabase.findAll();
    }

    private void validateDuplication(String userId) {
        if (UserDatabase.findUserById(userId) != null) {
            throw new UserValidationException("중복되는 아이디입니다.");
        }
    }

    private User createUser(Map<String, String> userInfo) {
        String userId = userInfo.get("userId");
        String password = userInfo.get("password");
        String name = userInfo.get("name");
        String email = userInfo.get("email");

        return User.of(userId, password, name, email);
    }
}
