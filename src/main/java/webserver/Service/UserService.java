package webserver.Service;

import db.UserDatabase;
import model.User;
import model.UserDto;
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

    public byte[] addUser(UserDto newUser) {
        UserDatabase.findUserById(newUser.getUserId()).ifPresent(m -> {
            throw HttpRequestException.builder()
                    .statusCode(StatusCodes.BAD_REQUEST)
                    .msg("<script>alert('사용자가 이미 존재합니다'); history.go(-1);</script>")
                    .build();
        });
        UserDatabase.addUser(User.from(newUser));
        return newUser.getUserId().getBytes();
    }

    public String login(String userId, String password) {
        User user = UserDatabase.findUserById(userId).orElseThrow(() ->
                HttpRequestException.builder()
                            .statusCode(StatusCodes.BAD_REQUEST)
                            .msg("<script>alert('존재하지 않는 계정입니다'); window.location.href = 'http://localhost:8080/user/login_failed.html';</script>")
                            .build()); //wrong ID
        if(!user.getPassword().equals(password))  //with wrong password
                throw HttpRequestException.builder()
                    .statusCode(StatusCodes.BAD_REQUEST)
                    .msg("<script>alert('패스워드가 올바르지 않습니다'); window.location.href = 'http://localhost:8080/user/login_failed.html';</script>")
                    .build();
        return user.getUserId(); // with valid password
    }
}

