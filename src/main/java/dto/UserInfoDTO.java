package dto;

import java.util.Map;

public class UserInfoDTO {
    private String userId;
    private String password;
    private String name;
    private String email;

    public UserInfoDTO(Map<String, String> queryString) {
        this.userId = queryString.get("userId");
        this.password = queryString.get("password");
        this.name = queryString.get("name");
        this.email = queryString.get("email");
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
