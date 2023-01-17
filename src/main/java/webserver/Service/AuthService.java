package webserver.Service;

import db.Database;
import model.User;
import webserver.domain.StatusCodes;
import webserver.exception.HttpRequestException;

public class AuthService {

    public byte[] join(String userId, String password, String name, String email) {
        Database.findUserById(userId).ifPresent(m -> {
            throw new HttpRequestException(StatusCodes.BAD_REQUEST, "<script>alert('사용자가 이미 존재합니다'); history.go(-1);</script>");
        });
        Database.addUser(new User(userId, password, name, email));
        return userId.getBytes();
    }

    public byte[] login(String userId, String password) {

        StringBuilder cookie = new StringBuilder();

        Database.findUserById(userId).ifPresentOrElse(user -> {  //if there's an id that is equivalent to given parameter
            if(user.getPassword().equals(password))  //with valid password
                cookie.append(user);
            else   // with invalid password
                throw new HttpRequestException(StatusCodes.BAD_REQUEST, "<script>alert('패스워드가 올바르지 않습니다'); history.go(-1);</script>");
        }, ()->{
            throw new HttpRequestException(StatusCodes.BAD_REQUEST, "<script>alert('id를 다시 입력해주세요'); history.go(-1);</script>");
        });

        return cookie.toString().getBytes();
    }
}

