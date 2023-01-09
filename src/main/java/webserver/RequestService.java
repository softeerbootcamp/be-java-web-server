package webserver;

import webserver.httpMock.CustomHttpRequest;
import webserver.httpMock.CustomHttpResponse;

public interface RequestService {
    public void handleRequest(CustomHttpRequest req, CustomHttpResponse res);

}
