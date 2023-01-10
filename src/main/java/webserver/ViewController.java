package webserver;

import http.HttpRequest;
import http.HttpResponse;
import http.RequestLine;
import http.Uri;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class ViewController implements Controller {

    @Override
    public void doService(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        RequestLine requestLine = httpRequest.getRequestLine();
        Uri uri = requestLine.getUri();
        byte[] body = Files.readAllBytes(new File("src/main/resources/templates" + uri.getPath()).toPath());
        httpResponse.response200Header(body.length);
        httpResponse.responseBody(body);
    }
}
