package request.method.GET;

import request.Request;
import request.method.GET.file.GETFileRequestEnum;
import request.method.GET.handlers.GETHandler;
import request.method.GET.handlers.GETHandlerEnum;
import request.method.GET.handlers.GETNoMatchHandler;

public class GETHandlerFactory {
    public static GETHandler generateHandler(Request request) {
        GETHandler handler = GETNoMatchHandler.getInstance();
        for(GETFileRequestEnum fileRequestEnum : GETFileRequestEnum.values()) {
            if(fileRequestEnum.supportsRequestedFilePostfix(fileRequestEnum, request)) {
                return fileRequestEnum.getHandler();
            }
        }
        for(GETHandlerEnum GETHandlerEnum : GETHandlerEnum.values()) {
            if(request.getResource().matches(GETHandlerEnum.getUrlRegEx())) {
                return GETHandlerEnum.getHandler();
            }
        }

        return handler;
    }
}
