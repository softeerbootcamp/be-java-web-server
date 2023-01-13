package controller;

import httpMock.CustomHttpRequest;
import httpMock.CustomHttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface RequestController {
    Logger logger = LoggerFactory.getLogger(RequestController.class);

    CustomHttpResponse handleRequest(CustomHttpRequest req);
}
