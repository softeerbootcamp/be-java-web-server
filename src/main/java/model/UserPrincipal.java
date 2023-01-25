package model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserPrincipal {

    public String userId;

    public String name;


    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setName(String username) {
        this.name = username;
    }

    public UserPrincipal from(User user){
        return new UserPrincipal(user.getUserId(), user.getName());
    }
}
