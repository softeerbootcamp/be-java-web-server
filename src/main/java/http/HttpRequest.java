package http;

import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

public class HttpRequest {
    private static final Logger logger = LoggerFactory.getLogger(HttpRequest.class);
    private HttpRequestLine httpRequestLine;
    private HttpHeader httpHeader;

    public HttpRequest(BufferedReader br) throws IOException {
        String line = br.readLine();
        if (line == null) return;
        httpHeader = new HttpHeader(HttpHeader.readHeaders(br));
        httpRequestLine = HttpRequestUtils.getRequestLine(line);
        String uri = httpRequestLine.getHttpUri().getUri();
        if (uri.startsWith("/user/create")) {
            int index = uri.indexOf("?");
            String requestPath = uri.substring(0, index);
            String queryString = uri.substring(index + 1);
            logger.debug("QueryString : {}", queryString);
            Map<String, String> params = HttpRequestUtils.parseQueryString(queryString);
            User user = new User(params.get("userId"), params.get("password"), params.get("name"), params.get("email"));
            logger.debug("User : {}", user);
        }
    }

    public String getUri() {
        return this.httpRequestLine.getHttpUri().getUri();
    }
}
