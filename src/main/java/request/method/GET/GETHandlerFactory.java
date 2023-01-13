package request.method.GET;

import request.Request;
import request.method.GET.handlers.GETHandler;
import request.method.GET.handlers.HandlerEnum;
import request.method.GET.handlers.NoMatchHandler;

public class GETHandlerFactory {
    public static GETHandler generateHandler(Request request) {
        GETHandler handler = NoMatchHandler.of();
        for(HandlerEnum handlerEnum : HandlerEnum.values()) {
            // TODO: resource를 enum의 url과 비교하는 것을 equals 메소드를 사용할 수 있도록 수정
            if(request.getResource().contains(handlerEnum.getUrl())) {
                return handlerEnum.getHandler();
            }
        }

        return handler;
    }
}
