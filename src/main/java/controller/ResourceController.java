package controller;

import http.request.HttpRequest;
import http.ContentType;
import http.response.HttpResponse;
import http.Uri;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

import static utils.FileIoUtils.load404ErrorFile;
import static utils.FileIoUtils.loadFile;

public class ResourceController implements Controller {

    public static final int EMPTY_LENGTH = 0;

    private static final Logger logger = LoggerFactory.getLogger(ResourceController.class);

    private final List<String> paths;
    public ResourceController() {
        this.paths = Arrays.asList(".html", ".ico", ".css", ".js", "woff", "ttf", "png");
    }
    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
        doGet(httpRequest, httpResponse);
    }

    @Override
    public boolean isUri(HttpRequest httpRequest) {
        Uri uri = httpRequest.getUri();
        return paths.stream().anyMatch(uri::isEndsWith);
    }

    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
        String path = httpRequest.getUri().getPath();
        ContentType contentType = ContentType.from(path);
        String filePath = contentType.getDirectory() + path;
        logger.debug("filePath: {}" + filePath);
        byte[] body = loadFile(filePath);

        if(body.length == EMPTY_LENGTH){
            byte[] errorBody = load404ErrorFile();
            httpResponse.do404(errorBody);
            return;
        }

        httpResponse.forward(contentType, body);
    }

}
