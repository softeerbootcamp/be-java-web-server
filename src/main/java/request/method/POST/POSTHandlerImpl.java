package request.method.POST;

import request.Request;
import request.method.HttpMethodHandler;
import request.method.POST.handlers.POSTHandler;
import response.Response;

import java.io.IOException;

public class POSTHandlerImpl implements HttpMethodHandler {
    private static final POSTHandlerImpl postHandlerImpl;

    static {
        postHandlerImpl = new POSTHandlerImpl();
    }

    public static POSTHandlerImpl getInstance() {
        return postHandlerImpl;
    }

    @Override
    public Response handle(Request request) throws IOException {
        POSTHandler postHandler = POSTHandlerFactory.generateHandler(request);
        return postHandler.handle(request);
    }
}
