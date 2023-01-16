package request.method.POST.handlers;

import request.Request;
import response.Response;

public interface POSTHandler {
    Response handle(Request request);
}
