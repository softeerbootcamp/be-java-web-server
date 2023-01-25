package model;

public class UserPrincipal {

    public String userId;

    public String name;

    public UserPrincipal(String userId, String username) {
        this.userId = userId;
        this.name = username;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String username) {
        this.name = username;
    }

    public UserPrincipal from(User user){
        return new UserPrincipal(user.getUserId(), user.getName());
    }
}
