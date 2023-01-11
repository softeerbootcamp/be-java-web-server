package Controller;

import http.HttpRequest;
import http.HttpResponse;

import java.io.IOException;

public interface Controller {
    HttpResponse makeResponse(HttpRequest httpRequest) throws IOException;
}
