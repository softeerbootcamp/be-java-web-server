package http.exception;

import http.common.HttpBody;
import http.common.HttpStatus;
import http.common.MediaType;
import http.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.ResourceUtils;

public final class HttpExceptionHandler {
    private HttpExceptionHandler() {}
    private static final String EXCEPTION_PREFIX = "[HTTP_EXCEPTION]";

    private static final Logger logger = LoggerFactory.getLogger(HttpExceptionHandler.class);
    
    public static void handle(HttpException e, HttpResponse response) {
        if (e.status == HttpStatus.NOT_FOUND) {
            notFoundExceptionHandler(response, e.getMessage());
        }
    }

    private static void notFoundExceptionHandler(HttpResponse response, String message) {
        logger.debug("{}  {} - {}", EXCEPTION_PREFIX, HttpStatus.NOT_FOUND, message);

        response.setStatus(HttpStatus.NOT_FOUND);
        HttpBody body = new HttpBody(ResourceUtils.loadFileFromClasspath("/not_found.html"));

        response.addHeader("Content-Type", MediaType.TEXT_HTML.name());
        response.setBody(body);
    }
}
