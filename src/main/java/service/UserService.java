package service;

import db.SessionDB;
import dto.LogInDTO;
import dto.SignUpDTO;
import http.common.Session;
import model.User;
import repository.UserRepository;

import java.util.List;

public class UserService {

    private final UserRepository userRepository = new UserRepository();

    public String signUp(SignUpDTO signUpDto) {
        checkLoginIdDuplicated(signUpDto);

        User user = User.of(signUpDto.getLoginId(), signUpDto.getPassword(), signUpDto.getName(), signUpDto.getEmail());
        userRepository.save(user);

        return user.getLoginId();
    }

    private void checkLoginIdDuplicated(SignUpDTO signUpDto) {
        userRepository.findByLoginId(signUpDto.getLoginId()).ifPresent(userDAO -> {
            throw new IllegalStateException("duplicated login id");
        });
    }

    public Session logIn(LogInDTO logInDto) {
        User user = validate(logInDto);
        Session session = new Session(user);
        SessionDB.addSession(session);
        return session;
    }

    private User validate(LogInDTO logInDto) {
        User user = userRepository.findByLoginId(logInDto.getLoginId()).orElseThrow(() -> {
            throw new IllegalStateException("not exist login id");
        });
        if (!user.getPassword().equals(logInDto.getPassword())) {
            throw new IllegalStateException("password not match");
        }
        return user;
    }

    public List<User> getAllUser() {
        return userRepository.findAll();
    }
}
