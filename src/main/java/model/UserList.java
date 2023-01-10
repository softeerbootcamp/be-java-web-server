package model;

import java.util.ArrayList;
import java.util.List;

public class UserList {

    private List<User> user = new ArrayList<>();

    public List<User> getUser() {
        return user;
    }

    public void setUser(User _user) {
        this.user.add(_user);
    }
}
