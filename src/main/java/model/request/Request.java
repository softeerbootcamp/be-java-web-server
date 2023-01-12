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
    private final byte[] body;

    private Request(RequestLine requestLine, Map<Header, String> headers, byte[] body) {
        this.requestLine = requestLine;
        this.headers = headers;
        this.body = body;
    }

    public static Request from(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));

        RequestLine requestLine = RequestLine.from(br.readLine());

        Map<Header, String> headers = makeHeaders(br);

        return new Request(requestLine, headers, null);
    }

    private static Map<Header, String> makeHeaders(BufferedReader br) throws IOException {
        Map<Header, String> headers = new HashMap<>();
        String header = br.readLine();

        while(!System.lineSeparator().equals(header)) {
            logger.debug("Header: {}", header);
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

    public byte[] getBody() {
        return body;
    }
}
