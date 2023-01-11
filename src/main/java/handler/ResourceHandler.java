package handler;

import http.request.HttpRequest;

public class ResourceHandler implements Handler{
    @Override
    public String handle(HttpRequest request) {
        String url = request.getUrl();
        if (url.equals("/")) {
            return "/index.html";
        }

        return url;
    }
}
