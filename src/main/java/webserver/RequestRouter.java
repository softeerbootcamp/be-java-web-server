package webserver;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.StaticFileService;
import service.UserAccountService;
import webserver.httpMock.CustomHttpRequest;
import webserver.httpMock.CustomHttpResponse;

import java.util.HashMap;
import java.util.Map;

public class RequestRouter {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static RequestRouter requestRouter;
    private final Map<String, RequestService> requestMap = new HashMap<>() {{
        put("[^\\s]+\\.(html|js|css|ico|ttf|woff|svg|eot|woff2|png)$", StaticFileService.get());
        put("(/user).*", UserAccountService.get());
    }};

    private RequestRouter() {
    }

    public static RequestRouter getRequestRouter() {
        if (requestRouter == null)
            requestRouter = new RequestRouter();
        return requestRouter;
    }

    public void doRoute(CustomHttpRequest req, CustomHttpResponse res) {
        for (String regex : requestMap.keySet()) {
            if (req.getUrl().matches(regex)) {
                requestMap.get(regex).handleRequest(req, res);
                logger.info(regex + " matches " + req.getUrl());
                return;
            }
        }
        RequestService.NOT_FOUND(res);
    }
}
