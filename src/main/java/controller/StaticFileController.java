package controller;

import http.response.HttpResponse;
import http.request.HttpRequest;
import model.Session;
import utils.*;

import java.util.UUID;


public class StaticFileController implements Controller {
    public final static String PATH = "/";
    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) {
        if (httpRequest.getHttpMethod().equals(HttpMethod.GET)) {
            doGet(httpRequest, httpResponse);
            return;
        }
        throw new IllegalArgumentException("유효하지 않은 HTTP 메서드입니다");
    }

    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        String sid = httpRequest.getSession();
        if (sid == null || !FileIoUtils.getExtension(httpRequest.getUri().getPath()).contains("html")) {
            httpResponse.setBody(FileIoUtils.loadFile(httpRequest.getUri().getPath()));
            httpResponse.setContentType(ContentType.getContentType(FileIoUtils.getExtension(httpRequest.getUri().getPath())));
            return;
        }
        try {
            Session session = SessionManager.getSession(UUID.fromString(sid));
            if (session != null) {
                httpResponse.setBody(FileIoUtils.makeLoggedInPage(httpRequest.getUri().getPath(), session));
                httpResponse.setContentType(ContentType.getContentType(FileIoUtils.getExtension(httpRequest.getUri().getPath())));
            }
            if (session == null) {
                httpResponse.setBody(FileIoUtils.loadFile(httpRequest.getUri().getPath()));
                httpResponse.setContentType(ContentType.getContentType(FileIoUtils.getExtension(httpRequest.getUri().getPath())));
            }
        }
        catch (Exception e) {
            httpResponse.setStatusCode(StatusCode.NOTFOUND);
        }
    }
}
