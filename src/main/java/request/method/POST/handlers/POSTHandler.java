package request.method.POST.handlers;

import request.Request;

public interface POSTHandler {
    byte[] handle(Request request);
}
