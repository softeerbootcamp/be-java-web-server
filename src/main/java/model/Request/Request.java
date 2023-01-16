package model.Request;

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

    private final RequestLine requestLine;
    private final Map<String, String> headers;
    private final Map<String, String> requestParams;

    public Request(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in, UTF_8));
        String line = br.readLine();
        this.requestLine = new RequestLine(line);
        logger.debug("> request Method : {}, URL : {}, Version : {}", requestLine.getMethod(), requestLine.getUrl(), requestLine.getVersion());

        //---------header -------//
        this.headers = extractHeaders(br, line);
        //--------- body ---------//
        this.requestParams = extractBody(br);
    }

    private Map<String, String> extractBody(BufferedReader br) throws IOException {
        if (headers.containsKey("Content-Length")) {
            int contentLength = Integer.parseInt(headers.get("Content-Length"));
            char[] body = new char[contentLength];
            br.read(body, 0, contentLength);
            System.out.println(body);
            return new HashMap<>(parseQuerystring(String.copyValueOf(body)));
        } else {
            return Map.of();
        }
    }

    private Map<String, String> extractHeaders(BufferedReader br, String line) throws IOException {
        Map<String, String> headers = new HashMap<>();
        while (!line.equals("")) {
            line = br.readLine();
            if (line == null) {
                break;
            }
            logger.debug("> header : {}", line);
            String[] headerTokens = line.split(": ");
            if (headerTokens.length == 2) {
                headers.put(headerTokens[0], headerTokens[1]);
            }
        }
        return headers;
    }

    public Map<String, String> getRequestParams() {
        return requestParams;
    }

    public String getUrl() {
        return this.requestLine.getUrl();
    }



}
