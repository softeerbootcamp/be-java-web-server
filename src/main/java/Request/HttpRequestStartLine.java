package Request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.FileIoUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

public class HttpRequestStartLine {
    private String path;
    private String protocol;
    private HttpMethod method;
    private static final String BLANK = " ";
    private static final Logger logger = LoggerFactory.getLogger(HttpRequestStartLine.class);

    public HttpRequestStartLine(String method, String path, String protocol) {
        this.path = path;
        this.protocol = protocol;
        this.method = HttpMethod.valueOf(method);
    }

    public static HttpRequestStartLine from(BufferedReader br) {
        String line = FileIoUtil.readLine(br);
        logger.debug("request : " + line);
        String[] temp = line.split(BLANK);
        return new HttpRequestStartLine(temp[0], temp[1], temp[2]);
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
}
