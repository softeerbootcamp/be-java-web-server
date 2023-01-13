package request.method.GET.handlers;

import request.Request;

public interface GETHandler {
    byte[] handle(Request request);
}
