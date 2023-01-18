package model.service;

import controller.StaticController;
import model.domain.User;
import model.repository.MemoryUserRepository;
import model.repository.UserRepository;

import java.util.Collection;
import java.util.Optional;

public class UserService {
    private final UserRepository userRepository = MemoryUserRepository.getInstance();
    private static UserService userService;

    private UserService(){}

    public static UserService getInstance(){
        if (userService == null){
            synchronized (StaticController.class){
                if (userService == null){
                    userService = new UserService();
                }
            }
        }
        return userService;
    }


    public String join(User user){
        try {
            ValidateDuplicate(user);
        } catch (IllegalStateException e){
            throw e;
        }
        userRepository.addUser(user);
        return user.getUserId();
    }

    private void ValidateDuplicate(User user) {
        userRepository.findUserById(user.getUserId()).ifPresent(a->{
            throw new IllegalStateException("중복된 아이디입니다. 다른 아이디를 사용해주세요.");
        });
    }

    public Collection<User> findUsers(){
        return userRepository.findAll();
    }

    public Optional<User> findOneUser(String userId){
        return userRepository.findUserById(userId);
    }

    public User login(String userId, String password){
        User user = findOneUser(userId).orElseThrow(()->new IllegalStateException("존재하지 않는 계정입니다."));
        if (!user.getPassword().equals(password))
            throw new IllegalStateException("존재하지 않는 계정입니다.");
        return user;
    }


}
