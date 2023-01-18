package service;

import db.UserRepository;
import model.User;

import java.util.Map;

public class UserService {
    private static final String USER_ID = "userId";
    private static final String PASSWORD = "password";
    private static final String NAME = "name";
    private static final String EMAIL = "email";

    public void create(Map<String, String> parameters) {
        User user = new User(
                parameters.get(USER_ID),
                parameters.get(PASSWORD),
                parameters.get(NAME),
                parameters.get(EMAIL)
        );
        UserRepository.addUser(user);
    }

    public void validateUser(String requestUserId, String requestPassword) {
        User user = UserRepository.findUserById(requestUserId);
        if (user == null || !requestPassword.equals(user.getPassword())) {
            throw new IllegalArgumentException("로그인 실패");
        }
    }

}
