package request;

import response.Response;

public interface RequestHandler {
    Response handle(Request request, int port);
}
