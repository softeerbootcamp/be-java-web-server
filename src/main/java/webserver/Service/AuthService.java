package webserver.Service;

import db.UserDatabase;
import model.User;
import webserver.domain.StatusCodes;
import webserver.exception.HttpRequestException;

public class AuthService {

    public byte[] join(String userId, String password, String name, String email) {
        UserDatabase.findUserById(userId).ifPresent(m -> {
            throw new HttpRequestException(StatusCodes.BAD_REQUEST, "<script>alert('사용자가 이미 존재합니다'); history.go(-1);</script>");
        });
        UserDatabase.addUser(new User(userId, password, name, email));
        return userId.getBytes();
    }

    public String login(String userId, String password) {
        User user = UserDatabase.findUserById(userId)
                .orElseThrow(() -> new HttpRequestException(StatusCodes.SEE_OTHER, "<script>alert('존재하지 않는 계정입니다');</script>", "/user/login_failed.html")); //wrong ID
        if(!user.getPassword().equals(password))  //with wrong password
            throw new HttpRequestException(StatusCodes.SEE_OTHER, "<script>alert('패스워드가 올바르지 않습니다');</script>", "/user/login_failed.html");
        return user.getUserId(); // with valid password
    }
}

