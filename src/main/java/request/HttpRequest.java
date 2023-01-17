package request;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.IOUtils;

public class HttpRequest {
    private static final String lineSeparator = System.lineSeparator();
    // 개행 문자 구분

    private static final Logger logger = LoggerFactory.getLogger(HttpRequest.class);
    private final RequestStartLine requestStartLine;
    private final RequestHeader header;
    private final String body;

    public HttpRequest(RequestStartLine requestStartLine, RequestHeader requestHeader, String body) {
        this.requestStartLine = requestStartLine;
        this.header = requestHeader;
        this.body = body;
    }

    public static HttpRequest of(InputStream inputStream) throws IOException {
        /* TODO : PR Feedback 수행 - refactoring [ RequestHeader, RequestBody 분리 ]*/

//        String line = br.readLine(); // Read StartLine
//        RequestStartLine requestLine = RequestStartLine.of(line);

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        RequestStartLine requestStartLine = RequestStartLine.of(bufferedReader);
        RequestHeader requestHeader = RequestHeader.of(bufferedReader);

        String contentLength = requestHeader.getContentLength();
        logger.debug("contentLength : {}",contentLength);

        if (Objects.isNull(contentLength)) {
            return new HttpRequest(requestStartLine, requestHeader, "");
        }

        String body = IOUtils.readData(bufferedReader, Integer.parseInt(contentLength));
        logger.debug("body : {}",body);

        return new HttpRequest(requestStartLine, requestHeader, body);
    }

    public String getPath() {
        if (requestStartLine.isTemplatesResource()) {
            logger.debug("templates request : {}" , String.format("./templates%s", requestStartLine.getPath()));
            return String.format("./templates%s", requestStartLine.getPath());
        }
        if (requestStartLine.isStaticResource()) {
            logger.debug("static request : {}" , String.format("./static%s", requestStartLine.getPath()));
            return String.format("./static%s", requestStartLine.getPath());
        }
        return requestStartLine.getPath();
    }

    public Map<String, String> getParameters() {
        return requestStartLine.getParameters();
    }

    public boolean isGet() {
        return requestStartLine.getMethod() == Method.GET;
    }

    public boolean isPost() {
        return requestStartLine.getMethod() == Method.POST;
    }

    public boolean isForStaticContent() {
        return requestStartLine.isTemplatesResource() || requestStartLine.isStaticResource();
    }

    public boolean isQueryContent() {
        return !isForStaticContent();
    }

    public String getBody() {
        return body;
    }
}
