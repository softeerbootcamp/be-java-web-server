package request.method.GET.handlers;

import model.User;
import request.Request;
import request.RequestParser;

public class UserRegisterHandler implements GETHandler{
    private final static UserRegisterHandler userRegisterHandler;

    static {
        userRegisterHandler = new UserRegisterHandler();
    }

    public static UserRegisterHandler of() {
        return userRegisterHandler;
    }

    @Override
    public byte[] handle(Request request) {
        User user = new User(RequestParser.parseGETQueryString(request.getResource()));
        System.out.println(user);
        return new byte[0];
    }
}
