package request;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpRequest {
    private static final String lineSeparator = System.lineSeparator();
    // 개행 문자 구분

    private static final Logger logger = LoggerFactory.getLogger(HttpRequest.class);
    private final RequestStartLine requestStartLine;
    private final String header;
    private final String body;

    public HttpRequest(RequestStartLine requestStartLine, String header) {
        this.requestStartLine = requestStartLine;
        this.header = header;
        this.body = "";
    }

    public static HttpRequest of(BufferedReader br) throws IOException {
        String line = br.readLine(); // Read StartLine
        RequestStartLine requestLine = RequestStartLine.of(line);
        logger.debug("Request Line : {}{}{}", lineSeparator, line, lineSeparator);
        StringBuilder header = new StringBuilder();
        while (!line.equals("")) { // Read Header
            line = br.readLine();
            logger.debug("Input Header : {}",line);
            header.append(line);
            header.append(lineSeparator); // Read Header 후 구분 공백 라인

            if (line == null) {
                break;
            }
        }
        logger.debug("Header{}{}", lineSeparator, header);

        return new HttpRequest(requestLine, header.toString());
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
