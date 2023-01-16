package request.method.GET.handlers;

import request.Request;

public class GETNoMatchHandler implements GETHandler {
    private final static GETNoMatchHandler GET_NO_MATCH_HANDLER;

    static {
        GET_NO_MATCH_HANDLER = new GETNoMatchHandler();
    }

    public static GETNoMatchHandler of() {
        return GET_NO_MATCH_HANDLER;
    }

    @Override
    public byte[] handle(Request request) {
        return null;
    }
}
