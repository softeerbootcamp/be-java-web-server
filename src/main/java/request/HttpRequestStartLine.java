package request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.FileIoUtil;

import java.io.BufferedReader;

public class HttpRequestStartLine {
    private String path;
    private String protocol;
    private HttpMethod method;
    private RequestDataType type;
    private static final String BLANK = " ";
    private static final Logger logger = LoggerFactory.getLogger(HttpRequestStartLine.class);

    public HttpRequestStartLine(String method, String path, String protocol, RequestDataType type) {
        this.path = path;
        this.protocol = protocol;
        this.method = HttpMethod.valueOf(method);
        this.type = type;
    }

    public static HttpRequestStartLine from(BufferedReader br) {
        String line = FileIoUtil.readLine(br);
        logger.debug("request : " + line);
        String[] temp = line.split(BLANK);

        return new HttpRequestStartLine(temp[0], temp[1], temp[2], RequestDataType.getUrlType(temp[1]));
    }
    public String getMappingUrl() {
        return type.getMappingUrl(path);
    }

    public RequestDataType getType() {
        return type;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getProtocol() {
        return protocol;
    }
    public void setPath(String path) {
        this.path = path;
    }
}
