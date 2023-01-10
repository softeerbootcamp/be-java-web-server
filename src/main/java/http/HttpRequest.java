package http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HttpRequest {

    private static final Logger logger = LoggerFactory.getLogger(HttpRequest.class);

    private final HttpRequestLine httpRequestLine;

    public HttpRequest(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String requestLine = br.readLine();
        if (requestLine == null) {
            httpRequestLine = null;
            return;
        }
        logger.debug("requestLine : {}", requestLine);

        this.httpRequestLine = new HttpRequestLine(requestLine);
    }
}
