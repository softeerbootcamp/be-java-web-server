package controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.HttpRequest;
import response.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;

public class StaticController extends AbstractController{

    private static final Logger logger = LoggerFactory.getLogger(StaticController.class);

    public HttpResponse get(HttpRequest httpRequest) throws IOException, URISyntaxException {
        String defaultPath = httpRequest.getDefaultPath();
        String path = defaultPath;

        logger.debug("getPath : {}",path);
        if (isTemplatesResource(defaultPath)) {
            path = String.format("./templates%s", defaultPath);
        }

        if (isStaticResource(defaultPath)) {
            path = String.format("./static%s", defaultPath);
        }

        return HttpResponse.ok()
                .bodyByPath(path)
                .build();
    }

    public boolean isForStaticContent(HttpRequest httpRequest) {
        String path = httpRequest.getDefaultPath();
        return isTemplatesResource(path) | isStaticResource(path);
    }

    private boolean isTemplatesResource(String path) {
        return path.endsWith("html") || path.equals("/favicon.ico");
    }

    private boolean isStaticResource(String path) {
        return !isTemplatesResource(path) && path.contains(".");
    }
}
