package request.method.POST.handlers;

import request.Request;

public class POSTNoMatchHandler implements POSTHandler {
    private static final POSTNoMatchHandler POST_NO_MATCH_HANDLER;

    static {
        POST_NO_MATCH_HANDLER = new POSTNoMatchHandler();
    }

    public static POSTHandler getInstance() {
        return POST_NO_MATCH_HANDLER;
    }

    @Override
    public byte[] handle(Request request) {
        return null;
    }
}
