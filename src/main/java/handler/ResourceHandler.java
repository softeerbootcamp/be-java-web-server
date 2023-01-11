package handler;

import http.request.HttpRequest;
import http.response.HttpResponse;
import http.response.HttpStatusCode;

public class ResourceHandler implements Handler{
    @Override
    public String handle(HttpRequest request, HttpResponse response) {
        response.setHttpStatusLine(request, HttpStatusCode.OK);
        response.addHttpHeader("Content-type", request.getHttpHeader("Accept"));

        String url = request.getUrl();
        if (url.equals("/")) {
            return "/index.html";
        }

        return url;
    }
}
