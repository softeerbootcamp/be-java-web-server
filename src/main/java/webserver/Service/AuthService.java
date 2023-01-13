package webserver.Service;

import db.Database;
import model.User;
import webserver.domain.StatusCodes;
import webserver.exception.HttpRequestException;

public class AuthService {

    public byte[] join(String userId, String password, String name, String email) {
        User user = Database.findUserById(userId);
        if(user == null){
            Database.addUser(new User(userId, password, name, email));
            return userId.getBytes();
        }
        throw new HttpRequestException(StatusCodes.BAD_REQUEST, "<script>alert('사용자가 이미 존재합니다'); history.go(-1);</script>");
    }

}
