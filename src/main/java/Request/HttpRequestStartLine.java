package Request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;

public class HttpRequestStartLine {
    private String path;
    private String protocol;
    private String method;
    private static final String BLANK = " ";
    private static final Logger logger = LoggerFactory.getLogger(HttpRequestStartLine.class);

    public HttpRequestStartLine(String method, String path, String protocol) {
        this.path = path;
        this.protocol = protocol;
        this.method = method;
    }

    public static HttpRequestStartLine from(BufferedReader br) {
        String line = null;
        try {
            line = br.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        logger.debug("request : " + line);
        String[] temp = line.split(BLANK);
        return new HttpRequestStartLine(temp[0], temp[1], temp[2]);
    }

    public String getPath() {
        return path;
    }

    public String getProtocol() {
        return protocol;
    }
}
