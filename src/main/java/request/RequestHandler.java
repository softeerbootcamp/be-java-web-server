package request;

import response.HttpResponseStatus;
import response.Response;
import service.SessionService;
import service.UserService;

public interface RequestHandler {
    UserService userService = UserService.getInstance();

    SessionService sessionService = SessionService.getInstance();

    default Response doGet(Request request) {
        return Response.from(HttpResponseStatus.METHOD_NOT_ALLOWED);
    }

    default Response doPost(Request request) {
        return Response.from(HttpResponseStatus.METHOD_NOT_ALLOWED);
    }

    default Response doPut(Request request) {
        return Response.from(HttpResponseStatus.METHOD_NOT_ALLOWED);
    }

    default Response doDelete(Request request) {
        return Response.from(HttpResponseStatus.METHOD_NOT_ALLOWED);
    }
}
