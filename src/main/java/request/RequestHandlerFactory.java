package request;

import request.handlers.NoMatchHandler;

public class RequestHandlerFactory {
    public static RequestHandler generateHandler(Request request) {
        String resource = request.getResource();
        RequestHandler handler = NoMatchHandler.getInstance();
        for(HandlerEnum handlerEnum : HandlerEnum.values()) {
            if(resource.matches(handlerEnum.getUrlRegEx())) {
                return handlerEnum.getHandler();
            }
        }

        return handler;
    }
}
