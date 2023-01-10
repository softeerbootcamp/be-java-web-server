package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class HttpResponse {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private HttpStatus status;
    private ContentType contentType;
    private final Map<String, String> headers;
    private final Map<String, String> cookies;
    private byte[] body;


    public HttpResponse() {
        this.status = null;
        this.contentType = null;
        this.headers = new HashMap<>();
        this.cookies = new HashMap<>();
    }

    public void addToHeader(String key, String val){
        headers.put(key,val);
    }

    public void setStatus(HttpStatus status){
        this.status = status;
    }

    public void setContentType(ContentType contentType) {
        this.contentType = contentType;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    public String getHeaderByKey(String key) {
        return headers.get(key);
    }

    public HttpStatus getStatus() {
        return status;
    }

    public ContentType getContentType() {
        return contentType;
    }

    public byte[] getBody() {
        return body;
    }


}
