package controller;

import exception.HttpMethodException;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.Session;
import utils.*;

import java.util.UUID;

public class HtmlFileController implements Controller {
    public static final String PATH = "html";

    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) {
        HttpMethod requestHttpMethod = httpRequest.getHttpMethod();
        if (HttpMethod.GET.equals(requestHttpMethod)) {
            doGet(httpRequest, httpResponse);
            return;
        }
        throw new HttpMethodException(requestHttpMethod);
    }

    private void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        String path = httpRequest.getUri().getPath();
        try {
            Session session = SessionManager.getSession(UUID.fromString(httpRequest.getSession())).orElse(null);
            httpResponse.setBody(FileIoUtils.makePage(path, session));
        } catch (NullPointerException e) {
            try {
                httpResponse.setBody(FileIoUtils.makePage(path, null));
            } catch (IllegalArgumentException err) {
                httpResponse.setStatusCode(StatusCode.NOTFOUND);
                httpResponse.setBody(FileIoUtils.loadFile(PathManager.ERROR_PATH));
                return;
            }
        }
        httpResponse.setContentType(ContentType.HTML);
    }
}
