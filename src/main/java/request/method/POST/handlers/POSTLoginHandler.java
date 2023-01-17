package request.method.POST.handlers;

import request.Request;
import response.Response;

public class POSTLoginHandler implements POSTHandler {
    private static final POSTLoginHandler POST_LOGIN_HANDLER;

    static {
        POST_LOGIN_HANDLER = new POSTLoginHandler();
    }

    public static POSTLoginHandler getInstance() {
        return POST_LOGIN_HANDLER;
    }

    @Override
    public Response handle(Request request) {
        return null;
    }
}
