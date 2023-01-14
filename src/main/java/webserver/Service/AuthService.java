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

}

