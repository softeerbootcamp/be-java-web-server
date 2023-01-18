package dto;

import java.util.Map;

public class LogInDTO {
    public static final String USER_ID = "userId";
    public static final String PASSWORD = "password";

    private String userId;
    private String password;

    public LogInDTO(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    public static LogInDTO of(Map<String, String> parameters) {
        return new LogInDTO(parameters.get(USER_ID), parameters.get(PASSWORD));
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }
}
