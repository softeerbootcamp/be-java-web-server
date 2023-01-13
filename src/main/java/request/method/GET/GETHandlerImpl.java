package request.method.GET;

import request.Request;
import request.method.GET.handlers.GETHandler;
import request.method.HttpMethodHandler;

import java.util.List;

public class GETHandlerImpl implements HttpMethodHandler {
    private static final GETHandlerImpl getHandlerImpl;

    static {
        getHandlerImpl = new GETHandlerImpl();
    }

    public static GETHandlerImpl of() {
        return getHandlerImpl;
    }

    @Override
    public byte[] handle(Request request) {
        for(GETFileRequestEnum fileRequestEnum : GETFileRequestEnum.values()) {
            if( contains(fileRequestEnum.getSupportingFilePostfix(fileRequestEnum), request.getResource() )) {
                return fileRequestEnum.handle(request);
            }
        }

        GETHandler handler = GETHandlerFactory.generateHandler(request);
        return handler.handle(request);
    }

    boolean contains(List<String> list, String resource) {
        for(String token : list) {
            if(resource.contains(token)) {
                return true;
            }
        }
        return false;
    }
}
