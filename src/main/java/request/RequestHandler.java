package request;

import response.Response;

public interface RequestHandler {
    Response handleRequest(Request request, int port);
}
