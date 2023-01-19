package controller;

import http.HttpSession;
import http.SessionHandler;
import http.request.HttpRequest;
import http.response.DynamicResolver;
import http.response.HttpResponse;
import http.response.HttpResponseFactory;
import util.FileIoUtil;

import java.io.File;
import java.nio.file.Files;

public class DynamicController implements Controller {

    @Override
    public HttpResponse doService(HttpRequest httpRequest) throws Exception {
        String url = httpRequest.getUrl();
        File file = FileIoUtil.getFile(httpRequest.getUri());
        byte[] body = Files.readAllBytes(file.toPath());

        if (url.endsWith(".html") && SessionHandler.validateSession(httpRequest.getSid())) {
            HttpSession httpSession = SessionHandler.getSession(httpRequest.getSid());
            body = DynamicResolver.showUserName(file, httpSession.getUserName());
        }

        if (url.endsWith(".html") && !SessionHandler.validateSession(httpRequest.getSid())) {
            body = DynamicResolver.hideLogoutButton(file);
        }

        return HttpResponseFactory.OK(httpRequest.getContentType(), body);
    }

    @Override
    public boolean isMatch(HttpRequest httpRequest) {
        return false;
    }
}
