package request.method.GET.handlers;

import model.User;
import request.Request;
import request.RequestParser;

public class GETUserRegisterHandler implements GETHandler{
    private final static GETUserRegisterHandler GET_USER_REGISTER_HANDLER;

    static {
        GET_USER_REGISTER_HANDLER = new GETUserRegisterHandler();
    }

    public static GETUserRegisterHandler of() {
        return GET_USER_REGISTER_HANDLER;
    }

    @Override
    public byte[] handle(Request request) {
        User user = new User(RequestParser.parseGETQueryString(request.getResource()));
        System.out.println(user);
        return new byte[0];
    }
}
