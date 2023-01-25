package model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.net.URLDecoder;
import java.util.Map;

@Getter
@AllArgsConstructor
public class UserDto {

    private String userId;
    private String password;
    private String name;
    private String email;

    public static UserDto from(Map<String, String> map){   //factory method only for reflection
        return new UserDto(map.get("userId"),
                           map.get("name"),
                           map.get("password"),
                           map.get("email"));
    }

}
