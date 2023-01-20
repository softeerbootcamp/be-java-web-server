package controller;

import httpMock.CustomHttpRequest;
import httpMock.CustomHttpResponse;

public interface RequestController {

    CustomHttpResponse handleRequest(CustomHttpRequest req);
}
