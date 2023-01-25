package service;

import Utility.UserValidation;
import exceptions.CustomException;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import repository.MemoryUserRepo;

import java.util.Map;

public class UserService {
    private static Logger logger = LoggerFactory.getLogger(UserService.class);

    public static User addUser(Map<String, String> bodyParams) {
        String userId = bodyParams.get("userId");
        String password = bodyParams.get("password");
        String name = bodyParams.get("name");
        String email = bodyParams.get("email");

        if (!UserValidation.isUserCreationFormValid(userId, password, name, email)) {
            throw new CustomException("email, password, name을 올바르게 입력해 주세요.");
        }

        if (MemoryUserRepo.getInstance().findUserById(userId).isPresent()) {
            throw new CustomException("userID duplicated");
        }

        logger.debug("회원 가입 완료 {}, {}, {}", userId, password, name);
        User user = new User(userId, password, name, email);
        MemoryUserRepo.getInstance().addUser(user);
        return user;
    }

    public static User getUserByReqbody(Map<String, String> bodyParams) {

        String userId = bodyParams.get("userId");
        String password = bodyParams.get("password");
        User customer = MemoryUserRepo.getInstance().findUserById(userId).orElse(null);
        if (customer != null && customer.getPassword().equals(password)) {
            return customer;
        }
        return User.GUEST;
    }
}