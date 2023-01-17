package service;

import model.User;

import java.util.Map;

public interface UserService {
    void join(User user);

    User findUser(String userId);

    void login(String userId, String password);

    void createUser(Map<String, String> params);
}
