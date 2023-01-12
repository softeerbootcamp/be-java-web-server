package controller;

import http.request.HttpRequest;
import http.response.ContentType;
import http.response.HttpResponse;
import http.request.Uri;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static utils.FileIoUtils.loadFile;

public class IndexController implements Controller {

    private static final String INDEX_PATH = "/index.html";

    private final List<String> paths;

    public IndexController(){
        this.paths = Collections.singletonList("/");
    }
    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        doGet(httpResponse);
    }

    @Override
    public boolean isUri(HttpRequest httpRequest) {
        Uri uri = httpRequest.getUri();
        return paths.stream().anyMatch(uri::isEndsWith);
    }

    public void doGet(HttpResponse httpResponse) throws IOException {
        String path = "/index.html";
        ContentType contentType = ContentType.from(path);
        String filePath = contentType.getDirectory() + path;
        byte[] body = loadFile(filePath);

        httpResponse.forward(contentType, body);
    }

}
