package model;

import java.net.URLDecoder;
import java.util.Map;

public class UserDto {

    private String userId;
    private String password;
    private String name;
    private String email;

    public UserDto(String userId, String password, String name, String email) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = URLDecoder.decode(email);
    }

    public static UserDto from(Map<String, String> map){   //factory method only for reflection
        return new UserDto(map.get("userId"),
                           map.get("name"),
                           map.get("password"),
                           map.get("email"));
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
}
