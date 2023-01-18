package controller;

import http.ContentType;
import http.request.HttpRequest;
import http.response.HttpResponse;

import java.io.IOException;

import static utils.FileIoUtils.loadFile;

public class IndexController extends AbstractController {

    private static final String INDEX_PATH = "/index.html";

    public IndexController() {
    }

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        String path = INDEX_PATH;
        ContentType contentType = ContentType.from(path);
        String filePath = contentType.getDirectory() + path;

        byte[] body = loadFile(filePath);

        httpResponse.forward(contentType, body);
    }

}
