package webserver;

import com.github.jknack.handlebars.internal.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.StaticFileService;
import webserver.httpMock.CustomHttpRequest;
import webserver.httpMock.CustomHttpResponse;
import webserver.httpMock.constants.ContentType;
import webserver.httpMock.constants.StatusCode;

import java.util.HashMap;
import java.util.Map;

public class RequestRouter {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static RequestRouter requestRouter;
    private final Map<String, RequestService> requestMap = new HashMap<>() {{
        put("[^\\s]+\\.(html|js|css|ico|ttf|woff|svg|eot|woff2|png)$", StaticFileService.get());
    }};

    private RequestRouter(){}

    public static RequestRouter getRequestRouter(){
        if(requestRouter == null)
            requestRouter = new RequestRouter();
        return requestRouter;
    }

    public void doRoute(CustomHttpRequest req, CustomHttpResponse res){
        for(String regex : requestMap.keySet()){
            if(req.getUrl().matches(regex)){
                requestMap.get(regex).doSomething(req, res);
                logger.info(regex + " matches "  + req.getUrl());
                return;
            }
        }
        logger.info("Do not match any url. url is " + req.getUrl());
        res.setStatusCode(StatusCode.OK);
        res.setContentType(ContentType.TEXT_PLAIN);
        res.addToBody(ArrayUtils.toObject("404 not found".getBytes()));
    }




}
