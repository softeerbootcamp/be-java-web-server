package http;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

public class HttpRequest {

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
        while (!line.equals("")) {
            requests.add(line);
            line = br.readLine();
        }
        RequestLine requestLine = RequestLine.from(requests.get(0));
        RequestHeader requestHeader = RequestHeader.from(requests.subList(1, requests.size()));

        if (requestHeader.hasContentLength()) {
            Map<String, String> requestBody = new HashMap<>();
            int contentLength = requestHeader.getContentLength();
            char[] body = new char[contentLength];
            br.read(body, 0, contentLength);
            String bodys = String.copyValueOf(body);
            requestBody = Stream.of(bodys.split("&"))
                    .map(q -> q.split("="))
                    .filter(q -> q.length == 2)
                    .collect((toMap(q -> q[0], q -> URLDecoder.decode(q[1], StandardCharsets.UTF_8))));
            return new HttpRequest(requestLine, requestHeader, requestBody);
        }
        return new HttpRequest(requestLine, requestHeader, new HashMap<>());
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
}
