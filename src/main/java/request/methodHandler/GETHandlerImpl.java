package request.methodHandler;

import request.Request;
import request.enums.GETRequestUrlEnum;

import java.util.List;

public class GETHandlerImpl implements HttpMethodHandler {
    @Override
    public byte[] handle(Request request) {
        for(GETRequestUrlEnum requestUrlEnum : GETRequestUrlEnum.values()) {
            if(this.contains(requestUrlEnum.getSupportingUrl(requestUrlEnum), request.getResource())) {
                return requestUrlEnum.handle(request);
            }
        }
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
