package controller;

import http.*;
import http.request.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;

public class ViewController implements Controller {

    @Override
    public HttpResponse doService(HttpRequest httpRequest) throws IOException {
        RequestLine requestLine = httpRequest.getRequestLine();
        RequestHeader requestHeader = httpRequest.getRequestHeader();
        HttpUri httpUri = requestLine.getHttpUri();
        ResourceType resourceType = httpUri.parseResourceType();
        String contentType = requestHeader.getContentType();
        byte[] body = Files.readAllBytes(new File("src/main/resources" + resourceType.getPath() + httpUri.getPath()).toPath());
        return HttpResponse.of(HttpStatus.OK, contentType, new HashMap<>(), body, requestLine.getVersion());
    }

    @Override
    public boolean isMatch(HttpRequest httpRequest) {
        return true;
    }
}
