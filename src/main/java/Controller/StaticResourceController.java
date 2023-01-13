package Controller;

import http.request.HttpRequest;
import http.response.HttpResponse;

public class StaticResourceController implements Controller {
    @Override
    public String process(HttpRequest request, HttpResponse response) {
        response.ok(request);

        String url = request.getUrl();
        if (url.equals("/")) {
            return "/index.html";
        }

        return url;
    }
}
