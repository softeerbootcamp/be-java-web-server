package controller;

import http.request.HttpRequest;
import http.response.DynamicResolver;
import http.response.HttpResponse;
import http.response.HttpResponseFactory;
import http.session.HttpSession;
import http.session.SessionHandler;
import util.FileIoUtil;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

public class ResourceController implements Controller {
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
        } catch (Exception e) {
            return HttpResponseFactory.NOT_FOUND("Not Found");
        }
    }

    private byte[] renderHtml(HttpRequest httpRequest, File file) throws Exception {
        HttpSession httpSession = SessionHandler.getSession(httpRequest.getSid());
        return DynamicResolver.createDynamicHtml(file, httpSession);
    }
}
