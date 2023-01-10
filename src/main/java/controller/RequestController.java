package controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import httpMock.CustomHttpRequest;
import httpMock.CustomHttpResponse;
import httpMock.constants.ContentType;
import httpMock.constants.StatusCode;

public interface RequestController {
    Logger logger = LoggerFactory.getLogger(RequestController.class);

    void handleRequest(CustomHttpRequest req, CustomHttpResponse res);

    static void NOT_FOUND(CustomHttpResponse res) {
        res.setStatusCode(StatusCode.NOT_FOUND);
        res.setContentType(ContentType.TEXT_PLAIN);
        res.addToBody("404 not found".getBytes());
    }
}
