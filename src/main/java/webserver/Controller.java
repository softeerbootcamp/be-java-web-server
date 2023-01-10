package webserver;

import http.HttpRequest;
import http.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;

public interface Controller {

    public void doService(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException;
}
