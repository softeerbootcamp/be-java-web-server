package dto;

import java.util.Map;

public class SignUpDTO extends LogInDTO {

    public static final String NAME = "name";
    public static final String EMAIL = "email";
    private String name;
    private String email;


    private SignUpDTO(String userId, String password, String name, String email) {
        super(userId, password);
        this.name = name;
        this.email = email;
    }

    public static SignUpDTO of(Map<String, String> parameters) {
        return new SignUpDTO(parameters.get(LOGIN_ID), parameters.get(PASSWORD), parameters.get(NAME), parameters.get(EMAIL));
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
