package webserver;

import http.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;

public class ViewController implements Controller {

    @Override
    public void doService(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        RequestLine requestLine = httpRequest.getRequestLine();
        RequestHeader requestHeader = httpRequest.getRequestHeader();
        Uri uri = requestLine.getUri();
        ResourceType resourceType = uri.parseResourceType();
        String contentType = requestHeader.getContentType();
        byte[] body = Files.readAllBytes(new File("src/main/resources" + resourceType.getPath() + uri.getPath()).toPath());
        httpResponse.response200Header(contentType, body.length);
        httpResponse.responseBody(body);
    }
}
