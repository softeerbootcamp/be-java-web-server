package request;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpRequest {
    private static final String lineSeparator = System.getProperty("line.separator");
    // 개행 문자 구분

    private static final Logger logger = LoggerFactory.getLogger(HttpRequest.class);
    private final RequestLine requestLine;
    private final String header;
    private final String body;

    public HttpRequest(RequestLine requestLine, String header) {
        this.requestLine = requestLine;
        this.header = header;
        this.body = "";
    }

    public static HttpRequest of(BufferedReader br) throws IOException {
        String line = br.readLine();

        RequestLine requestLine = RequestLine.of(line);
        logger.debug("Request Line{}{}{}", lineSeparator, line, lineSeparator);

        StringBuilder header = new StringBuilder();
        while (!line.equals("")) {
            line = br.readLine();
            header.append(line);
            header.append(lineSeparator);

            if (line == null) {
                break;
            }
        }
        logger.debug("Header{}{}", lineSeparator, header);

        return new HttpRequest(requestLine, header.toString());
    }

    public String getPath() {
        if (requestLine.isTemplatesResource()) {
            return String.format("./templates%s", requestLine.getPath());
        }
        if (requestLine.isStaticResource()) {
            return String.format("./static%s", requestLine.getPath());
        }
        return requestLine.getPath();
    }

    public Map<String, String> getParameters() {
        return requestLine.getParameters();
    }

    public boolean isGet() {
        return requestLine.getMethod() == Method.GET;
    }

    public boolean isPost() {
        return requestLine.getMethod() == Method.POST;
    }

    public boolean isForStaticContent() {
        return requestLine.isTemplatesResource() || requestLine.isStaticResource();
    }

    public boolean isForDynamicContent() {
        return !isForStaticContent();
    }

    public String getBody() {
        return body;
    }
}
