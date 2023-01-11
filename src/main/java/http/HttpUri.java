package http;

public class HttpUri {
    private String uri;

    public HttpUri(String uri) {
        this.uri = uri;
    }

    public String getUri() {
        return uri;
    }

    // uri에 . 이 있으면 정적 파일을 주면 되지 않을까?
    public boolean isStaticFileUri(){
        return uri.contains(".");
    }
}
