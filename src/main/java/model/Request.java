package model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;
import static util.HttpRequestUtils.parseQuerystring;

public class Request {
    private static final Logger logger = LoggerFactory.getLogger(Request.class);

    private final String method;
    private final String url;
    private final Map<String, String> headers;
    private final Map<String, String> requestParams;

    //TODO 생성자 메서드의 가독성을 더 높일 수 있는 방법
    public Request(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in, UTF_8));

        //--------req line -----//
        String line = br.readLine();
        logger.debug("> request line : {}", line);

        String[] tokens = line.split(" ");
        this.method = tokens[0];

        String url = tokens[1];
        if (url.equals("/")) {
            url = "/index.html";
        }
        this.url = url;

        //---------header -------//
        Map<String, String> headers = new HashMap<>();
        while (!line.equals("")) {
            line = br.readLine();

            logger.debug("> header : {}", line);
            String[] headerTokens = line.split(": ");
            if (headerTokens.length == 2) {
                headers.put(headerTokens[0], headerTokens[1]);
            }
        }
        this.headers = headers;

        //--------- body ---------//
        if (headers.containsKey("Content-Length")) {
            int contentLength = Integer.parseInt(headers.get("Content-Length"));
            char[] body = new char[contentLength];
            br.read(body, 0, contentLength);
            System.out.println(body);
            this.requestParams = createRequestParams(String.copyValueOf(body));
        } else {
            this.requestParams = Map.of();
        }
    }

    public Map<String, String> createRequestParams(String body) {
        return new HashMap<>(parseQuerystring(body));
    }

    public String getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public Map<String, String> getRequestParams() {
        return requestParams;
    }
}
