package dto;

import java.util.Map;

public class LoginDTO {
    public static final String LOGIN_ID = "loginId";
    public static final String PASSWORD = "password";

    private String loginId;
    private String password;

    public LoginDTO(String loginId, String password) {
        this.loginId = loginId;
        this.password = password;
    }

    public static LoginDTO of(Map<String, String> parameters) {
        return new LoginDTO(parameters.get(LOGIN_ID), parameters.get(PASSWORD));
    }

    public String getLoginId() {
        return loginId;
    }

    public String getPassword() {
        return password;
    }
}
