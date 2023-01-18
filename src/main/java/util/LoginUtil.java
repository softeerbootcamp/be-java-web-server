package util;

import Request.HttpRequest;
import model.User;

import java.util.Objects;

public class LoginUtil {
    public static boolean checkUserInfoMatch(HttpRequest httpRequest) {
        try {
            User user = ManageDB.findUserById(httpRequest.getParams().get("userId"));
            return Objects.equals(user.getPassword(), httpRequest.getParams().get("password"));
        } catch (NullPointerException e){
            return false;
        }
    }
}
