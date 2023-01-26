package controller;

import http.request.HttpRequest;
import http.response.DynamicResolver;
import http.response.HttpResponse;
import http.response.HttpResponseFactory;
import http.session.HttpSession;
import http.session.SessionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.FileIoUtil;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ResourceController implements Controller {

    private static final Logger logger = LoggerFactory.getLogger(ResourceController.class);
    private static final ResourceController resourceController = new ResourceController();

    private ResourceController() {
    }

    public static ResourceController getInstance() {
        return resourceController;
    }

    @Override
    public HttpResponse doService(HttpRequest httpRequest) {
        try {
            String url = httpRequest.getUrl();
            File file = FileIoUtil.getFile(httpRequest.getUri());
            byte[] body = Files.readAllBytes(Path.of(file.getPath()));
            if (url.endsWith(".html")) {
                body = renderHtml(httpRequest, file);
            }
            return HttpResponseFactory.OK(httpRequest.getContentType(), body);
        } catch (IOException e) {
            logger.error(e.getMessage());
            return HttpResponseFactory.NOT_FOUND(e.getMessage());
        }
    }

    private byte[] renderHtml(HttpRequest httpRequest, File file) {
        HttpSession httpSession = SessionHandler.getSession(httpRequest.getSid());
        return DynamicResolver.createDynamicHtml(file, httpSession);
    }
}
