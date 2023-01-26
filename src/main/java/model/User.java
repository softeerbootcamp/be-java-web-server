package model;

public class User {
    public static final String ID = "id";
    public static final String LOGIN_ID = "login_id";
    public static final String PASSWORD = "password";
    public static final String NAME = "name";
    public static final String EMAIL = "email";

    private Long id;
    private String loginId;
    private String password;
    private String name;
    private String email;

    private User(Long id, String loginId, String password, String name, String email) {
        this.id = id;
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public static User of(String loginId, String password, String name, String email) {
        return new User(null, loginId, password, name, email);
    }

    public static User of(Long id, String loginId, String password, String name, String email) {
        return new User(id, loginId, password, name, email);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getLoginId() {
        return loginId;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "User [loginId=" + loginId + ", password=" + password + ", name=" + name + ", email=" + email + "]";
    }
}
