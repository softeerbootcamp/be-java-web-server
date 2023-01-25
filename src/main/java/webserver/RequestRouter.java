package webserver;


import controller.PostController;
import controller.RequestController;
import controller.StaticFileController;
import controller.UserAccountController;
import httpMock.CustomHttpFactory;
import httpMock.CustomHttpRequest;
import httpMock.CustomHttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class RequestRouter {
    private static final Logger logger = LoggerFactory.getLogger(RequestRouter.class);
    private static RequestRouter requestRouter;
    private final Map<String, RequestController> requestMap = new HashMap<>() {{
        put("/user", UserAccountController.get());
        put("/qna", PostController.get());
    }};

    private RequestRouter() {
    }

    public static RequestRouter getRequestRouter() {
        if (requestRouter == null)
            requestRouter = new RequestRouter();
        return requestRouter;
    }

    public CustomHttpResponse handleRequest(CustomHttpRequest req) {

        for (String keys : requestMap.keySet()) {
            if (req.getUrl().startsWith(keys)) {
                logger.info(keys + " matches " + req.getUrl());
                return requestMap.get(keys).handleRequest(req);
            }
        }

        if (StaticFileController.ifFileTypeRequested(req.getUrl())) {
            return StaticFileController.get().handleRequest(req);
        }

        return CustomHttpFactory.NOT_FOUND();
    }
}
