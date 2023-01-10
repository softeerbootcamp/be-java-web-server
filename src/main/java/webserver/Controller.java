package webserver;

import http.HttpRequest;
public interface Controller {

    public void doService(HttpRequest httpRequest);
}
