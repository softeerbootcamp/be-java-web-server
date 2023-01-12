package Controller;

import http.request.HttpRequest;
import http.response.HttpResponse;

public class ResourceController implements Controller {
    @Override
    public String process(HttpRequest request, HttpResponse response) {
        response.ok(request);

        String url = request.getUrl();
        if (url.equals("/")) {
            return "/index.html";
        }

        // TODO 잘못된 URL이 들어올 경우 404 NOT_FOUND 응답
        return url;
    }
}
