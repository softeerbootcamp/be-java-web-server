package webserver;

import webserver.httpMock.CustomHttpRequest;
import webserver.httpMock.CustomHttpResponse;

public interface RequestService {
    public void doSomething(CustomHttpRequest req, CustomHttpResponse res);

}
