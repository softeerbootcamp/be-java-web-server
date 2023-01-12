package handler;

import http.request.HttpRequest;
import http.response.HttpResponse;

public class ResourceHandler implements Handler{
    @Override
    public String handle(HttpRequest request, HttpResponse response) {
        response.ok(request);

        String url = request.getUrl();
        if (url.equals("/")) {
            return "/index.html";
        }

        return url;
    }
}
