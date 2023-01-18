package Controller;

import http.request.HttpRequest;
import http.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import view.ViewResolver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class StaticResourceController implements Controller {

    private static final Logger logger = LoggerFactory.getLogger(StaticResourceController.class);

    private static final List<String> supportedFileExtensions = List.of(".html", ".css", ".eot", ".svg", ".ttf", ".woff", ".woff2", ".png", ".js", ".ico");

    @Override
    public void process(HttpRequest request, HttpResponse response) {
        String url = request.getUrl();
        if (url.equals("/") || url.equals("/index.html")) {
            url = "/index.html";

            if (request.isLogin()) {
                HomeLoginController.getInstance().process(request, response);
                return;
            }
        }

        try {
            String viewPath = ViewResolver.resolveViewName(url);
            byte[] body = Files.readAllBytes(new File(viewPath).toPath());

            response.ok(request);
            response.setBodyMessage(body);
        } catch (IOException e) {
            logger.error(e.getMessage());
            logger.error("해당 경로에 파일이 존재하지 않습니다.");

            response.notFound(request);
        }
    }

    public static boolean isSupported(String url) {
        return supportedFileExtensions.stream().anyMatch(url::endsWith);
    }
}