package model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import model.dto.UserDto;

import java.net.URLDecoder;

@Builder
@Getter
@AllArgsConstructor
public class User {

    private String userId;
    private String password;
    private String name;
    private String email;


    public static User from(UserDto dto){
        return new User(dto.getUserId(), dto.getPassword(), dto.getName(), dto.getEmail());
    }

    public UserPrincipal toPrincipal(){
        return new UserPrincipal(userId, name);
    }

}
