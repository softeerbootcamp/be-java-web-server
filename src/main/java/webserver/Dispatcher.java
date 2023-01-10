package webserver;

import controller.Controller;
import http.common.HttpBody;
import http.common.HttpStatus;
import http.common.URI;
import http.request.HttpRequest;
import http.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.ResourceUtils;

import java.util.Map;
import java.util.Objects;

public class Dispatcher {
    private static final Logger logger = LoggerFactory.getLogger(Dispatcher.class);

    private final static Map<String, Controller> controllers;

    static {
        controllers = Map.of();
    }

    public static void handle(HttpRequest request, HttpResponse response) {
        setFile(request.getUri(), response);

        Controller controller = controllers.get(request.getUri().getPath());
        if (Objects.nonNull(controller)) {
            controller.execute(request, response);
            return;
        }

        if (response.getBody().size() == 0) {
            response.setStatus(HttpStatus.NOT_FOUND);
            logger.error("{} file not found", request.getUri().getPath());
        }
    }

    private static void setFile(URI uri, HttpResponse response) {
        HttpBody body = new HttpBody(
                ResourceUtils.loadFileFromClasspath(uri.getPath())
        );
        response.setBody(body);
        // TODO: MedeyType Enum 만들어서 처리하기
        response.addHeader("Content-Type", "text/html;charset=utf-8");
    }
}
