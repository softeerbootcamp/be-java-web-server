package request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

public class RequestStartLine {

    private static final Logger logger = LoggerFactory.getLogger(RequestStartLine.class);

    private final Method method;
    private final Uri uri;
    private final HttpVersion version;

    public RequestStartLine(Method method, Uri uri, HttpVersion version) {
        this.method = method;
        this.uri = uri;
        this.version = version;
    }

    public static RequestStartLine of(BufferedReader bufferedReader) throws IOException {
//        if (!bufferedReader.ready()) {
//            logger.debug("[RequestStartLine] Not ready : {}", bufferedReader.readLine());
//            throw new RuntimeException("HTTP 요청메시지의 요청라인이 비어있습니다.");
//        }
        String source = bufferedReader.readLine();
        logger.debug("[RequestStartLine] source : {}",source);
        String[] sources = source.split("\\s");
        logger.debug("[RequestStartLine] sources : {}", sources[0]);
        Method method = Method.of(sources[0]);
        Uri uri = Uri.of(sources[1]);
        HttpVersion version = HttpVersion.of(sources[2]);

        logger.debug("[RequestStartLine] method : {}",method.getMethodName());
        logger.debug("[RequestStartLine] uri : {}",uri.getPath());
        logger.debug("[RequestStartLine] version : {}",version.getVersion());

        return new RequestStartLine(method, uri, version);
    }

    public boolean isTemplatesResource() {
        return uri.isTemplatesResource();
    }

    public boolean isStaticResource() {
        return uri.isStaticResource();
    }

    public Method getMethod() {
        return method;
    }

    public String getPath() {
        return uri.getPath();
    }

    public Map<String, String> getParameters() {
        return uri.getParameters();
    }
}
