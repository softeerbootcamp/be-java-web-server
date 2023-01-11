package Request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Map;

public class HttpRequest {
    private final HttpRequestParams httpRequestParams;
    private final HttpRequestHeaders httpRequestHeaders;
    private final HttpRequestStartLine httpRequestStartLine;

    public HttpRequest(HttpRequestStartLine httpRequestStartLine,HttpRequestParams httpRequestParams, HttpRequestHeaders httpRequestHeaders) {
        this.httpRequestParams = httpRequestParams;
        this.httpRequestHeaders = httpRequestHeaders;
        this.httpRequestStartLine = httpRequestStartLine;
    }

    public String getPath(){
        return httpRequestStartLine.getPath();
    }

    public Map<String, String> getParams() {
        return httpRequestParams.getParams();
    }
}
