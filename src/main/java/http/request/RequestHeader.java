package http.request;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toMap;

public class RequestHeader {
    private final Map<String, String> headers;

    private RequestHeader(Map<String, String> headers) {
        this.headers = headers;
    }

    public static RequestHeader from(List<String> requestHeader) {
        return requestHeader.stream()
                .map(header -> header.split(": "))
                .collect(collectingAndThen(toMap(header -> header[0], header -> header[1]), RequestHeader::new));
    }

    public String getHeader(String key) {
        return headers.get(key);
    }

    public String getContentType() {
        return Arrays.stream(
                headers.get("Accept")
                        .split(","))
                .findFirst()
                .orElseThrow();
    }

    public boolean hasContentLength() {
        return headers.containsKey("Content-Length");
    }

    public int getContentLength() {
        return Integer.parseInt(headers.get("Content-Length"));
    }
}
