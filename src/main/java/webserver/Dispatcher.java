package webserver;

import controller.Controller;
import controller.SignUpController;
import http.common.HttpBody;
import http.common.MediaType;
import http.common.URI;
import http.exception.NotFoundException;
import http.request.HttpRequest;
import http.response.HttpResponse;
import util.ResourceUtils;

import java.util.Map;
import java.util.Objects;

public class Dispatcher {
    private final static Map<String, Controller> controllers;

    static {
        controllers = Map.of(
                SignUpController.PATH, new SignUpController()
        );
    }

    public static void handle(HttpRequest request, HttpResponse response) {
        setFile(request.getUri(), response);

        Controller controller = controllers.get(request.getUri().getPath());
        if (Objects.nonNull(controller)) {
            controller.execute(request, response);
            return;
        }

        if (response.getBody().size() == 0) {
            throw new NotFoundException("페이지를 찾을 수 없습니다.");
        }
    }

    private static void setFile(URI uri, HttpResponse response) {
        HttpBody body = new HttpBody(
                ResourceUtils.loadFileFromClasspath(uri.getPath())
        );
        response.setBody(body);

        int idxOfDot = uri.getPath().lastIndexOf(".");
        String extension = uri.getPath().substring(idxOfDot + 1);
        MediaType mediaType = MediaType.fromExtension(extension).orElse(MediaType.TEXT_PLAIN);

        response.addHeader("Content-Type", mediaType.getType());
    }
}
