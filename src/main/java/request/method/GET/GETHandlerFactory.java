package request.method.GET;

import request.Request;
import request.method.GET.handlers.GETHandler;
import request.method.GET.handlers.GETHandlerEnum;
import request.method.GET.handlers.GETNoMatchHandler;

public class GETHandlerFactory {
    public static GETHandler generateHandler(Request request) {
        GETHandler handler = GETNoMatchHandler.of();
        for(GETHandlerEnum GETHandlerEnum : GETHandlerEnum.values()) {
            // TODO: resource를 enum의 url과 비교하는 것을 equals 메소드를 사용할 수 있도록 수정
            if(request.getResource().contains(GETHandlerEnum.getUrl())) {
                return GETHandlerEnum.getHandler();
            }
        }

        return handler;
    }
}
