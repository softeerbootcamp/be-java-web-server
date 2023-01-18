package controller;

import http.ContentType;
import http.request.HttpRequest;
import http.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;

import static utils.FileIoUtils.load404ErrorFile;
import static utils.FileIoUtils.loadFile;

public class ResourceController extends AbstractController {

    private static final Logger logger = LoggerFactory.getLogger(ResourceController.class);

    public ResourceController() {
    }

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
        try {
            String path = httpRequest.getUri().getPath();
            ContentType contentType = ContentType.from(path);
            String filePath = contentType.getDirectory() + path;

            logger.debug("filePath: {}", filePath);

            byte[] body = loadFile(filePath);

            httpResponse.forward(contentType, body);

        } catch (IllegalArgumentException e) {
            byte[] errorBody = load404ErrorFile();
            httpResponse.do404(errorBody);
        }
    }

}
