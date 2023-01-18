package util;

import Request.HttpRequest;
import db.SessionDb;
import model.Session;
import model.User;

import java.util.Objects;

public class LoginUtil {
    public static boolean checkUserInfoMatch(HttpRequest httpRequest) {
        try {
            User user = UserDbUtil.findUserById(httpRequest.getParams().get("userId"));
            return Objects.equals(user.getPassword(), httpRequest.getParams().get("password"));
        } catch (NullPointerException e){
            return false;
        }
    }
    public static User checkSession(HttpRequest httpRequest) throws NullPointerException{
        String cookie = httpRequest.getHttpRequestHeaders().get("Cookie");
        return SessionDb.getSession(cookie).getUser();
    }
}
