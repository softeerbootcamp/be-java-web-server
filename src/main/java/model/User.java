package model;

import lombok.Getter;
import model.dto.UserDto;

import java.net.URLDecoder;

@Getter
public class User extends UserPrincipal{
    private String password;
    private String email;

    public User(String userId, String password, String name, String email) {
        super(userId, name);
        this.password = password;
        this.email = URLDecoder.decode(email);
    }



    public static User from(UserDto dto){
        return new User(dto.getUserId(), dto.getPassword(), dto.getName(), dto.getEmail());
    }
}
