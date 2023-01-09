package webserver;

import com.github.jknack.handlebars.internal.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.httpMock.CustomHttpRequest;
import webserver.httpMock.CustomHttpResponse;
import webserver.httpMock.constants.ContentType;
import webserver.httpMock.constants.StatusCode;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class RequestRouter {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static RequestRouter requestRouter;
    public final Map<String, RequestService> requestMap = new HashMap<>() {{
        put("[^\\s]+\\.(html|js|css|ico|ttf|woff|svg|eot|woff2|png)$", (req, res) -> getFile(req, res));
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
    }

    public void getFile(CustomHttpRequest req, CustomHttpResponse res){
        String filePath = null;
        try {
            URL resoucePath = getClass().getClassLoader().getResource("./static");
            String fileType = getFileTypeFromUrl(req.getUrl());
            res.setContentType(ContentType.getContentTypeByFileType(fileType));

            if(req.getUrl().endsWith("ico") || req.getUrl().endsWith("html")){
                resoucePath = getClass().getClassLoader().getResource("./templates");
            }

            filePath = resoucePath.getPath();
            byte[] file = Files.readAllBytes(new File(filePath + req.getUrl()).toPath());
            res.addToBody(ArrayUtils.toObject(file));
            res.setStatusCode(StatusCode.OK);

        } catch (IOException e) {
            logger.error("file not found at " + filePath + req.getUrl());
            throw new RuntimeException(e);
        }
    }

    private String getFileTypeFromUrl(String url){
        int index = url.lastIndexOf(".");
        return url.substring(index+1);
    }


}
