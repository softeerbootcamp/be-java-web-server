package dto;

import java.util.Map;

public class UserInfoDTO {

    public static final String USER_ID = "userId";
    public static final String PASSWORD = "password";
    public static final String NAME = "name";
    public static final String EMAIL = "email";

    private String userId;
    private String password;
    private String name;
    private String email;

    private UserInfoDTO(Map<String, String> args) {
        this.userId = args.get(USER_ID);
        this.password = args.get(PASSWORD);
        this.name = args.get(NAME);
        this.email = args.get(EMAIL);
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
