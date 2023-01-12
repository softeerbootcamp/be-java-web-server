package dto;

import java.util.Map;

public class UserInfoDTO {
    private String userId;
    private String password;
    private String name;
    private String email;

    public UserInfoDTO(Map<String, String> args) {
        this.userId = args.get("userId");
        this.password = args.get("password");
        this.name = args.get("ame");
        this.email = args.get("email");
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
