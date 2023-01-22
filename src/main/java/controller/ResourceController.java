package controller;

import http.HttpSession;
import http.SessionHandler;
import http.request.HttpRequest;
import http.response.DynamicResolver;
import http.response.HttpResponse;
import http.response.HttpResponseFactory;
import util.FileIoUtil;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;

public class ResourceController implements Controller {
    private static final ResourceController resourceController = new ResourceController();

    private ResourceController() {
    }

    public static ResourceController getInstance() {
        return resourceController;
    }
    @Override
    public HttpResponse doService(HttpRequest httpRequest) throws IOException {
        String url = httpRequest.getUrl();
        File file = FileIoUtil.getFile(httpRequest.getUri());
        byte[] body = Files.readAllBytes(file.toPath());
        if (url.endsWith(".html")) {
            body = renderHtml(httpRequest, file);
        }

        return HttpResponseFactory.OK(httpRequest.getContentType(), body);
    }

    private byte[] renderHtml(HttpRequest httpRequest, File file) throws IOException {
        HttpSession httpSession = SessionHandler.getSession(httpRequest.getSid());
        if (Objects.nonNull(httpSession)) {
            return DynamicResolver.showUserName(file, httpSession.getUserName());
        }
        return DynamicResolver.hideLogoutButton(file);
    }
    @Override
    public boolean isMatch(HttpRequest httpRequest) {
        return true;
    }
}
