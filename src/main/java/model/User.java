package model;

import webserver.domain.RequestMethod;
import webserver.domain.request.Request;

import java.net.URLDecoder;
import java.util.Map;

public class User extends UserPrincipal{
    private String password;
    private String email;

    public User(String userId, String password, String name, String email) {
        super(userId, name);
        this.password = password;
        this.email = URLDecoder.decode(email);
    }


    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public static User from(UserDto dto){
        return new User(dto.getUserId(), dto.getPassword(), dto.getName(), dto.getEmail());
    }
}
