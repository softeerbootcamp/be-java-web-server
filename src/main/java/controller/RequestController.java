package controller;

import httpMock.CustomHttpRequest;
import httpMock.CustomHttpResponse;
import httpMock.constants.ContentType;
import httpMock.constants.StatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

public interface RequestController {
    Logger logger = LoggerFactory.getLogger(RequestController.class);

    static CustomHttpResponse NOT_FOUND() {
        return CustomHttpResponse.of(
                StatusCode.NOT_FOUND,
                ContentType.TEXT_PLAIN,
                new HashMap<>(),
                "404 not found".getBytes()
        );
    }

    CustomHttpResponse handleRequest(CustomHttpRequest req);
}
