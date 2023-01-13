package Response;

import Request.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.FileIoUtil;
import util.HttpResponseUtil;
import util.StatusCode;

import java.nio.file.Path;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class HttpResponse {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);
    private static final String NEWLINE = "\r\n";
    private static final byte[] EMPTYBYTE = "".getBytes();
    private byte[] body;
    private StatusCode status;
    private String protocol;
    private Map<String, String> headers;

    public HttpResponse(byte[] body, StatusCode status, String protocol, Map<String, String> headers) {
        this.body = body;
        this.status = status;
        this.protocol = protocol;
        this.headers = headers;
    }

    public static HttpResponse createHttpResponse(String requestPath, StatusCode statusCode, String requestProtocol) {
        Path path = FileIoUtil.mappingDirectoryPath(requestPath);
        byte[] body = EMPTYBYTE;
        if (Objects.nonNull(path) && path.toFile().exists())
            body = HttpResponseUtil.generateBody(path);

        Map<String, String> headers = HttpResponseUtil.generateHeaders(requestPath, statusCode, body.length);
        return new HttpResponse(body, statusCode, requestProtocol, headers);
    }

    public void putHeaders(String type, String value) {
        this.headers.put(type, value);
    }

    public byte[] getBody() {
        return body;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    public String getHeaders() {
        return this.protocol + " " + this.status.toString() + NEWLINE + this.headers.entrySet().stream().map(e -> e.getKey() + ": " + e.getValue())
                .collect(Collectors.joining(NEWLINE)) + NEWLINE;
    }
}
