package request.method.GET.handlers;

import request.Request;
import response.Response;

public interface GETHandler {
    Response handle(Request request);
}
