package Controller;

import http.HttpRequest;
import http.HttpResponse;
import http.HttpStatus;

import java.io.IOException;

public class IndexController implements Controller{

    @Override
    public HttpResponse makeResponse(HttpRequest httpRequest) throws IOException {
        byte[] responseBody = HttpResponse.makeBody(httpRequest);
        return new HttpResponse(HttpStatus.OK, responseBody);
    }
}
