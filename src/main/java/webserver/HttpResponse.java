package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class HttpResponse {

    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);

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

    public Set<String> getCookieKeys() {
        return cookies.keySet();
    }

    public Map<String,String> getCookies(){
        return cookies;
    }

    public String getCookieByKey(String key) {
        return cookies.get(key);
    }

    public void addCookie(String key, String value) {
        cookies.put(key, value);
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    public String getHeaderByKey(String key) {
        return headers.get(key);
    }

    public Set<String> getHeaderKeys() {
        return headers.keySet();
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

    public void redirect(String redirectUrl) {
        this.status = HttpStatus.FOUND;
        headers.put("Location", redirectUrl);
    }

    public void of(String filePath, HttpStatus status,ContentType contentType){
        byte[] body = FileIoUtils.loadFileFromClasspath(filePath);
        setStatus(status);
        setContentType(contentType);
        addToHeader("Content-Length", String.valueOf(body.length));
        setBody(body);//body에는 요청한 파일 내용이 들어감
    }


}
