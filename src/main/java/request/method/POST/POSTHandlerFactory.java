package request.method.POST;

import request.Request;
import request.method.POST.handlers.POSTHandler;
import request.method.POST.handlers.POSTHandlerEnum;
import request.method.POST.handlers.POSTNoMatchHandler;

public class POSTHandlerFactory {
    public static POSTHandler generateHandler(Request request) {
        POSTHandler postHandler = POSTNoMatchHandler.getInstance();
        for(POSTHandlerEnum postHandlerEnum : POSTHandlerEnum.values()) {
            if(request.getResource().equals(postHandlerEnum.getUrl())) {
                postHandler = postHandlerEnum.getPostHandler();
            }
        }

        return postHandler;
    }
}
