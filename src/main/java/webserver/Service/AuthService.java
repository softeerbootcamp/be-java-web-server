package webserver.Service;

import db.Database;
import model.User;
import webserver.domain.StatusCodes;
import webserver.exception.HttpRequestException;

import java.util.Optional;

public class AuthService {

    public byte[] join(String userId, String password, String name, String email) {
        Optional<User> user = Database.findUserById(userId);
        user.ifPresent(m -> {
            System.out.println("asd");
            throw new HttpRequestException(StatusCodes.BAD_REQUEST, "<script>alert('사용자가 이미 존재합니다'); history.go(-1);</script>");
        });
        System.out.println("Null");
        Database.addUser(new User(userId, password, name, email));
        return userId.getBytes();
    }

}

