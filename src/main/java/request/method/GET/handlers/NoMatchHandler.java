package request.method.GET.handlers;

import request.Request;

public class NoMatchHandler implements GETHandler {
    private final static NoMatchHandler noMatchHandler;

    static {
        noMatchHandler = new NoMatchHandler();
    }

    public static NoMatchHandler of() {
        return noMatchHandler;
    }

    @Override
    public byte[] handle(Request request) {
        return null;
    }
}
