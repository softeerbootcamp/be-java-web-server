package http.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

public class HttpRequest {

    private static final Logger logger = LoggerFactory.getLogger(HttpRequest.class);

    private final RequestLine requestLine;
    private final RequestHeader requestHeader;
    private final Map<String, String> requestBody;

    public HttpRequest(
            RequestLine requestLine,
            RequestHeader requestHeader,
            Map<String, String> requestBody
    ) {
        this.requestLine = requestLine;
        this.requestHeader = requestHeader;
        this.requestBody = requestBody;
    }

    public static HttpRequest from(BufferedReader br) throws IOException {
        String line = br.readLine();
        List<String> requests = new ArrayList<>();
        logger.debug("Request : {}", line);
        while (!line.equals("")) {
            requests.add(line);
            line = br.readLine();
            logger.debug("Request : {}", line);
        }
        RequestLine requestLine = RequestLine.from(requests.get(0));
        RequestHeader requestHeader = RequestHeader.from(requests.subList(1, requests.size()));

        if (requestHeader.hasContentLength()) {
            int contentLength = requestHeader.getContentLength();
            char[] body = new char[contentLength];
            br.read(body, 0, contentLength);
            logger.debug("Request : {}", String.copyValueOf(body));
            return new HttpRequest(
                    requestLine,
                    requestHeader,
                    parseRequestBody(String.copyValueOf(body)
                    ));
        }
        return new HttpRequest(
                requestLine,
                requestHeader,
                new HashMap<>());
    }

    private static Map<String, String> parseRequestBody(String body) {
        return Stream.of(body.split("&"))
                .map(q -> q.split("="))
                .filter(q -> q.length == 2)
                .collect((toMap(q -> q[0], q -> URLDecoder.decode(q[1], StandardCharsets.UTF_8))));
    }

    public RequestLine getRequestLine() {
        return requestLine;
    }

    public RequestHeader getRequestHeader() {
        return requestHeader;
    }

    public Map<String, String> getRequestBody() {
        return requestBody;
    }

    public String getSid() {
        String cookie = requestHeader.getHeader("Cookie");
        if (Objects.isNull(cookie)) {
            return "";
        }
        Map<String, String> cookies = Stream.of(cookie.split("; "))
                .map(q -> q.split("="))
                .collect((toMap(q -> q[0], q -> q[1])));
        return cookies.get("sid");
    }

    public String getHttpVersion() {
        return requestLine.getVersion();
    }

    public String getUrl() {
        return requestLine.getUrl();
    }

    public HttpUri getUri() {
        return requestLine.getHttpUri();
    }

    public String getContentType() {
        return requestHeader.getContentType();
    }

    public HttpMethod getHttpMethod() {
        return requestLine.getHttpMethod();
    }
}
