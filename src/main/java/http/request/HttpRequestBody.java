package http.request;

public class HttpRequestBody {
    private final String content;

    private HttpRequestBody(String content) {
        this.content = content;
    }

    public static HttpRequestBody from(String content) {
        return new HttpRequestBody(content);
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "HttpRequestBody{" +
                "content='" + content + '\'' +
                '}';
    }
}
