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
        }else{
            StatusCodes error = StatusCodes.findStatus("INTERNAL_SERVER_ERROR");
            throw new HttpRequestException(error.getStatusCode(), error.getStatusMsg());
        }
    }

}
