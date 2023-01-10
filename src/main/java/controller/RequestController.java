package webserver;

import com.github.jknack.handlebars.internal.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.httpMock.CustomHttpRequest;
import webserver.httpMock.CustomHttpResponse;
import webserver.httpMock.constants.ContentType;
import webserver.httpMock.constants.StatusCode;

public interface RequestService {
    Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    void handleRequest(CustomHttpRequest req, CustomHttpResponse res);

    static void NOT_FOUND(CustomHttpResponse res) {
        res.setStatusCode(StatusCode.NOT_FOUND);
        res.setContentType(ContentType.TEXT_PLAIN);
        res.addToBody(ArrayUtils.toObject("404 not found".getBytes()));
    }
}
