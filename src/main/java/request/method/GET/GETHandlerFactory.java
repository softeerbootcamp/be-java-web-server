package request.method.GET;

import request.Request;
import request.method.GET.handlers.GETHandler;
import request.method.GET.handlers.HandlerEnum;
import request.method.GET.handlers.NoMatchHandler;

public class GETHandlerFactory {
    public static GETHandler generateHandler(Request request) {
        GETHandler handler = NoMatchHandler.of();
        for(HandlerEnum handlerEnum : HandlerEnum.values()) {
            if(request.getResource().contains(handlerEnum.getUrl())) {
                return handlerEnum.getHandler();
            }
        }

        return handler;
    }
}
