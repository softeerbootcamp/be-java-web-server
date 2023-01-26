package webserver.domain.response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;
import webserver.domain.ContentType;
import webserver.domain.StatusCodes;
import webserver.utils.StaticResourceFinder;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Response {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private StatusCodes status;
    private Map<String, String> headerMaps = new HashMap<>();
    private byte[] body;

    public byte[] getBody() {
        return body;
    }

    public Map<String, String> getHeader() {
        return headerMaps;
    }

    public StatusCodes getStatusCode(){
        return this.status;
    }


    public void addHeaderAndBody(StatusCodes statusCode, byte[] bodyAsByte, ContentType contentType){
        status = statusCode;
        body = bodyAsByte;
        headerMaps.put("Content-Type" , contentType.getType());
        headerMaps.put("Content-Length", String.valueOf(body.length));
    }
    public void addRedirection(StatusCodes statusCode, String redirectUri) {
        status = statusCode;
        headerMaps.put("Location", redirectUri);
    }

    public void notFoundError(StatusCodes statusCode){
        status = statusCode;
        try {
            StaticResourceFinder.staticFileResolver("/error.html").ifPresent(fileAsByte-> {
                body = fileAsByte;
                headerMaps.put("Content-Length", String.valueOf(body.length));
            });
            headerMaps.put("Content-Type" , ContentType.TEXT_HTML.getType());
        } catch (IOException e) {
            logger.debug("Can not implement IO operation");
        }
    }

    public void addCookieOnHeader(String cookie){
        headerMaps.put("Set-Cookie" , cookie);
    }

    public boolean isEmpty(){
        return (status == null);
    }
}
