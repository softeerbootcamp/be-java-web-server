package model.service;

import model.domain.User;
import model.repository.UserRepository;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String join(User user){
        ValidateDuplicate(user);
        userRepository.addUser(user);
        return user.getUserId();
    }

    private void ValidateDuplicate(User user) {
        userRepository.findUserById(user.getUserId()).ifPresent(a->{
            throw new IllegalStateException("중복된 아이디입니다. 다른 아이디를 사용해주세요.");
        });
    }
}
