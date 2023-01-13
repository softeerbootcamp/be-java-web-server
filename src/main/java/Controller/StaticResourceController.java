package Controller;
import http.request.HttpRequest;
import http.response.HttpResponse;

import java.util.List;

public class StaticResourceController implements Controller {

    private static final List<String> supportedFileExtensions = List.of(".html", ".css", ".eot", ".svg", ".ttf", ".woff", ".woff2", ".png", ".js", ".ico");

    @Override
    public String process(HttpRequest request, HttpResponse response) {
        response.ok(request);
        String url = request.getUrl();
        if (url.equals("/")) {
            return "/index.html";
        }

        return url;
    }

    public static boolean isSupported(String url) {
        return supportedFileExtensions.stream().anyMatch(url::endsWith);
    }
}