package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;

import java.io.FileNotFoundException;

public class RequestDispatcher {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final String TEMPLATES_DIR = "./templates";
    private static final String STATIC_DIR = "./static";
    private static final String CONTENT_LENGTH_HEADER_KEY = "Content-Length";
    private static final String MESSAGE_UNSUPPORTED_EXTENSION = "지원되지 않는 확장자 입니다.";
    private static final String EXTENSION_DELIMITER = "\\.";


    public static void handle(HttpRequest request, HttpResponse response) {
        try {
            String url = request.getUrl();

            serveFile(STATIC_DIR + url, response);
            serveFile(TEMPLATES_DIR + url, response);

        } catch (Exception e) {
            logger.error("Error is occurred while processing request", e);
        }
        if (response.getStatus() == null) {
            response.setStatus(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * 파싱한 url에 해당하는 파일을 파라미터의 HttpResponse에 헤더와 함께 담음
     * @param url
     * @param res
     */
    private static void serveFile(String url, HttpResponse res) {
        try {
            byte[] body = FileIoUtils.loadFileFromClasspath(url);
            ContentType contentType = extractExtension(url);

            res.setStatus(HttpStatus.OK);
            res.setContentType(contentType);
            res.addToHeader(CONTENT_LENGTH_HEADER_KEY, String.valueOf(body.length));
            res.setBody(body);//body에는 요청한 파일 내용이 들어감
        } catch (FileNotFoundException e) {
            logger.error("File not found for {}", url);
        } catch (Exception e) {
            logger.error("Error: {}", e.getMessage());
        }
    }

    private static ContentType extractExtension(String url) {
        String[] tokens = url.split(EXTENSION_DELIMITER);
        return ContentType.fromExtension(tokens[tokens.length - 1])
                .orElseThrow(() -> new IllegalArgumentException(MESSAGE_UNSUPPORTED_EXTENSION));
    }

}
