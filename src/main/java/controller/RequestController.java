package controller;

import httpMock.CustomHttpRequest;
import httpMock.CustomHttpResponse;
import httpMock.constants.ContentType;
import httpMock.constants.StatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface RequestController {
    Logger logger = LoggerFactory.getLogger(RequestController.class);

    static CustomHttpResponse NOT_FOUND() {
        CustomHttpResponse res = new CustomHttpResponse();
        res.setStatusCode(StatusCode.NOT_FOUND);
        res.setContentType(ContentType.TEXT_PLAIN);
        res.setBody("404 not found".getBytes());
        return res;
    }

    CustomHttpResponse handleRequest(CustomHttpRequest req);
}
