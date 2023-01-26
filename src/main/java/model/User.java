package model;

import java.util.Map;

public class User {
    private String id;
    private String pwd;
    private String username;
    private String email;

    private User(String id, String pwd, String username, String email) {
        this.id = id;
        this.pwd = pwd;
        this.username = username;
        this.email = email;
    }

    public static User of(String userId, String password, String name, String email) {
        return new User(userId, password, name, email);
    }

    public static User from(Map<String, String> parsedQueryString) {
        String userId = parsedQueryString.get("userId");
        String password = parsedQueryString.get("password");
        String name = parsedQueryString.get("name");
        String email = parsedQueryString.get("email");
        return new User(userId, password, name, email);
    }

    public String getId() {
        return id;
    }

    public String getPwd() {
        return pwd;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "User [userId=" + id + ", password=" + pwd + ", name=" + username + ", email=" + email + "]";
    }
}
