package Response;

import Request.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.ContentType;
import util.FileIoUtil;
import util.HttpResponseUtil;
import util.StatusCode;

import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class HttpResponse {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);
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

    public static HttpResponse createHttpResponse(HttpRequest httpRequest, StatusCode statusCode) {
        Path path = FileIoUtil.mappingPath(httpRequest.getPath());
        byte[] body = null;
        if (Objects.nonNull(path))
            body = HttpResponseUtil.generateBody(path);

        String protocol = httpRequest.getProtocol();

        ContentType contentType = ContentType.PLAIN;
        if (statusCode.equals(StatusCode.OK)) {
            String ex = FileIoUtil.findExtension(httpRequest.getPath());
            contentType = ContentType.valueOf(ex.toUpperCase());
        }

        Map<String, String> headers = new LinkedHashMap<>();                //headers
        headers.put("Content-Type", contentType.getContentText());
        headers.put("Content-Length", String.valueOf(body == null ? 0 : body.length));

        return new HttpResponse(body, statusCode, protocol, headers);
    }

    public void putHeaders(String type, String value) {
        this.headers.put(type, value);
    }

    public byte[] getBody() {
        return body;
    }

    public String getHeaders() {
        return this.protocol + " " + this.status.toString() + " \r\n" + this.headers.entrySet().stream().map(e -> e.getKey() + ": " + e.getValue())
                .collect(Collectors.joining(" \r\n")) + "\r\n";
    }
}
