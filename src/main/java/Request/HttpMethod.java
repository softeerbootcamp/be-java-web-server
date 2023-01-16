package Request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.FileIoUtil;

import java.io.BufferedReader;
import java.util.HashMap;

public enum HttpMethod {
    GET {
        @Override
        public HttpRequest getRequestByMethod(BufferedReader br, HttpRequestStartLine httpRequestStartLine) {
            HttpRequestParams httpRequestParams;
            try {
                String query = FileIoUtil.splitQuery(httpRequestStartLine.getPath());
                httpRequestParams = HttpRequestParams.of(query);
            } catch (RuntimeException e) {
                httpRequestParams = new HttpRequestParams(new HashMap<>());
            }
            HttpRequestHeaders httpRequestHeaders = HttpRequestHeaders.from(br);
            return new HttpRequest(httpRequestStartLine, httpRequestParams, httpRequestHeaders);
        }
    },
    POST {
        @Override
        public HttpRequest getRequestByMethod(BufferedReader br, HttpRequestStartLine httpRequestStartLine) {
            HttpRequestHeaders httpRequestHeaders = HttpRequestHeaders.from(br);
            int length = Integer.parseInt(httpRequestHeaders.getHeaders().get("Content-Length"));
            String data = FileIoUtil.readBuffer(br, length);
            HttpRequestParams httpRequestParams = HttpRequestParams.of(data);
            return new HttpRequest(httpRequestStartLine, httpRequestParams, httpRequestHeaders);
        }
    };
    private static final Logger logger = LoggerFactory.getLogger(HttpMethod.class);

    public abstract HttpRequest getRequestByMethod(BufferedReader br, HttpRequestStartLine httpRequestStartLine);

}
