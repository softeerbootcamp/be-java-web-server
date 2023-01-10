package service;

import model.User;

public interface UserService {
    void join(User user);

    User findUser(String userId);
}
