package model.request;

import model.general.Header;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class Request {
    private static final Logger logger = LoggerFactory.getLogger(Request.class);

    private final RequestLine requestLine;
    private final Map<Header, String> headers;
    private final RequestBody body;

    private Request(RequestLine requestLine, Map<Header, String> headers, RequestBody body) {
        this.requestLine = requestLine;
        this.headers = headers;
        this.body = body;
    }

    public static Request from(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));

        RequestLine requestLine = RequestLine.from(br.readLine());

        Map<Header, String> headers = makeHeaders(br);

        String contentLength = headers.get(Header.from("Content-Length"));
        if(contentLength == null)
            return new Request(requestLine, headers, null);

        RequestBody requestBody = RequestBody.of(br, Integer.parseInt(contentLength));
        logger.debug("body: {}", requestBody);

        return new Request(requestLine, headers, requestBody);
    }

    private static Map<Header, String> makeHeaders(BufferedReader br) throws IOException {
        Map<Header, String> headers = new HashMap<>();
        String header = br.readLine();

        while (!"".equals(header)) {
            logger.debug("Request Header: {}", header);
            String[] headerSplit = header.split(": ");
            headers.put(Header.from(headerSplit[0]), headerSplit[1]);
            header = br.readLine();
        }

        return headers;
    }

    public RequestLine getRequestLine() {
        return requestLine;
    }

    public Map<Header, String> getHeaders() {
        return headers;
    }

    public RequestBody getBody() {
        return body;
    }

    public String getSessionId() {
        String cookie = headers.get(Header.COOKIE);
        return cookie.split("=")[1];
    }
}
