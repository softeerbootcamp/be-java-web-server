package request.method.GET;

import request.Request;
import request.method.HttpMethodHandler;

import java.util.List;

public class GETHandlerImpl implements HttpMethodHandler {
    @Override
    public byte[] handle(Request request) {
        for(GETFileRequestEnum fileRequestEnum : GETFileRequestEnum.values()) {
            if( contains(fileRequestEnum.getSupportingFilePostfix(fileRequestEnum), request.getResource() )) {
                return fileRequestEnum.handle(request);
            }
        }
        // TODO: factory 같은 걸로 요청 url에 따른 handler 획득 및 처리
        return null;
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
