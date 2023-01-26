package service;

import db.UserRepository;
import exception.InvalidPasswordException;
import exception.LogInFailedException;
import exception.UserNotFoundException;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Collection;
import java.util.Map;

public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void addUser(Map<String, String> parameters) throws SQLIntegrityConstraintViolationException {
        userRepository.addUser(User.from(parameters));
    }

    public void validateUser(String requestUserId, String requestPassword) throws LogInFailedException {
        try {
            validatePassword(requestPassword, validateId(requestUserId).getPassword());
        } catch (UserNotFoundException | InvalidPasswordException | NullPointerException e) {
            throw new LogInFailedException("로그인 실패");
        }
    }

    private User validateId(String requestUserId) throws UserNotFoundException {
        return userRepository.findUserById(requestUserId);
    }

    private void validatePassword(String requestPassword, String password) throws InvalidPasswordException {
        if(!requestPassword.equals(password)) {
            throw new InvalidPasswordException();
        }
    }
    public Collection<User> getUserList() {
        return userRepository.findAll();
    }

    public String getUserName(String id) throws UserNotFoundException {
        return userRepository.findUserById(id).getName();
    }

}
