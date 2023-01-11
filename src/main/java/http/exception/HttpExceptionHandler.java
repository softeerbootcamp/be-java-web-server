package http.exception;

import http.common.HttpBody;
import http.common.HttpStatus;
import http.common.MediaType;
import http.request.HttpRequest;
import http.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.ResourceUtils;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public final class HttpExceptionHandler {
    private HttpExceptionHandler() {}
    private static final String EXCEPTION_PREFIX = "[HTTP_EXCEPTION]";

    private static final Logger logger = LoggerFactory.getLogger(HttpExceptionHandler.class);
    
    public static void handle(HttpRequest request, HttpResponse response, HttpException e) {
        if (e.status == HttpStatus.NOT_FOUND) {
            notFoundExceptionHandler(request, response, e.getMessage());
        }
    }

    private static void notFoundExceptionHandler(HttpRequest request, HttpResponse response, String message) {
        logger.debug("{} {} [{}] - {}",
                EXCEPTION_PREFIX, response.getStatus(),
                URLDecoder.decode(request.getPath(), StandardCharsets.UTF_8)
                , message);

        response.setStatus(HttpStatus.NOT_FOUND);
        HttpBody body = new HttpBody(ResourceUtils.loadFileFromClasspath("/not_found.html"));

        response.addHeader("Content-Type", MediaType.TEXT_HTML.name());
        response.setBody(body);
    }
}
