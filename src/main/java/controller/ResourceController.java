package controller;

import http.request.HttpRequest;
import http.ContentType;
import http.response.HttpResponse;
import http.Uri;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static utils.FileIoUtils.loadFile;

public class ResourceController implements Controller {

    private static final Logger logger = LoggerFactory.getLogger(ResourceController.class);

    private final List<String> paths;
    public ResourceController() {
        this.paths = Arrays.asList(".html", ".ico", ".css", ".js", "woff", "ttf", "png");
    }
    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        doGet(httpRequest, httpResponse);
    }

    @Override
    public boolean isUri(HttpRequest httpRequest) {
        Uri uri = httpRequest.getUri();
        return paths.stream().anyMatch(uri::isEndsWith);
    }

    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        String path = httpRequest.getUri().getPath();
        ContentType contentType = ContentType.from(path);
        String filePath = contentType.getDirectory() + path;
        logger.debug("filePath: {}" + filePath);
        byte[] body = loadFile(filePath);

        if(body.length == 0){
            httpResponse.do404();
            return;
        }

        httpResponse.forward(contentType, body);
    }

}
