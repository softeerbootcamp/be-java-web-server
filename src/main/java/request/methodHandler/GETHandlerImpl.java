package request.methodHandler;

import request.Request;
import request.methodHandler.RequestUrlEnum.GETRequestUrlEnum;

import java.io.DataOutputStream;
import java.util.List;

public class GETHandlerImpl implements HttpMethodHandler {
    private final DataOutputStream dos;

    public GETHandlerImpl(DataOutputStream dos) {
        this.dos = dos;
    }

    @Override
    public void handle(Request request) {
        for(GETRequestUrlEnum requestUrlEnum : GETRequestUrlEnum.values()) {
            if(this.contains(requestUrlEnum.getSupportingUrl(requestUrlEnum), request.getResource())) {
                requestUrlEnum.handle(request, dos);
                return;
            }
        }
        //404 exception
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
