package webserver.Service;

import db.UserDatabase;
import model.User;
import webserver.controller.UserController;
import webserver.domain.StatusCodes;
import webserver.exception.HttpRequestException;

import java.net.URLDecoder;

public class UserService {

    private UserService (){}

    public static UserService getInstance(){
        return UserService.LazyHolder.INSTANCE;
    }

    private static class LazyHolder{   //Singleton
        private static final UserService INSTANCE = new UserService();
    }

    public byte[] addUser(String userId, String password, String name, String email) {
        UserDatabase.findUserById(userId).ifPresent(m -> {
            throw new HttpRequestException(StatusCodes.BAD_REQUEST, "<script>alert('사용자가 이미 존재합니다'); history.go(-1);</script>");
        });
        UserDatabase.addUser(new User(userId, password, name, URLDecoder.decode(email)));
        return userId.getBytes();
    }

    public String login(String userId, String password) {
        User user = UserDatabase.findUserById(userId)
                .orElseThrow(() -> new HttpRequestException(StatusCodes.BAD_REQUEST, "<script>alert('존재하지 않는 계정입니다'); window.location.href = 'http://localhost:8080/user/login_failed.html';</script>")); //wrong ID
        if(!user.getPassword().equals(password))  //with wrong password
            throw new HttpRequestException(StatusCodes.BAD_REQUEST, "<script>alert('패스워드가 올바르지 않습니다'); window.location.href = 'http://localhost:8080/user/login_failed.html';</script>");
        return user.getUserId(); // with valid password
    }
}

