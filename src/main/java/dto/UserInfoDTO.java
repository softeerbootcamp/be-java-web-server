package dto;

import java.util.Map;

public class UserInfoDTO {
    private String userId;
    private String password;
    private String name;
    private String email;

    private UserInfoDTO(Map<String, String> args) {
        this.userId = args.get("userId");
        this.password = args.get("password");
        this.name = args.get("ame");
        this.email = args.get("email");
    }

    public static UserInfoDTO of(Map<String, String> parameters) {
        return new UserInfoDTO(parameters);
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
