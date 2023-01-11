package webserver;


import controller.RequestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import controller.StaticFileController;
import controller.UserAccountController;
import httpMock.CustomHttpRequest;
import httpMock.CustomHttpResponse;

import java.util.HashMap;
import java.util.Map;

public class RequestRouter {
    private static final Logger logger = LoggerFactory.getLogger(RequestRouter.class);
    private static RequestRouter requestRouter;
    private final Map<String, RequestController> requestMap = new HashMap<>() {{
        put("/user", UserAccountController.get());
    }};

    private RequestRouter() {
    }

    public static RequestRouter getRequestRouter() {
        if (requestRouter == null)
            requestRouter = new RequestRouter();
        return requestRouter;
    }

    public void doRoute(CustomHttpRequest req, CustomHttpResponse res) {
        res.setProtocolVersion(req.getProtocolVersion());

        if(StaticFileController.ifFileTypeRequested(req.getUrl())) {
            StaticFileController.get().handleRequest(req, res);
            return;
        }

        for (String keys : requestMap.keySet()) {
            if (req.getUrl().startsWith(keys)) {
                requestMap.get(keys).handleRequest(req, res);
                logger.info(keys + " matches " + req.getUrl());
                return;
            }
        }

        RequestController.NOT_FOUND(res);
    }
}
