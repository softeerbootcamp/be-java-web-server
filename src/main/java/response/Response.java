package response;

public class Response {
    private byte[] file;
    private HttpResponseStatus httpResponseStatus;

    private int code;

    private Response(byte[] file, HttpResponseStatus httpResponseStatus) {
        this.file = file;
        this.httpResponseStatus = httpResponseStatus;
    }

    private Response(HttpResponseStatus httpResponseStatus) {
        this.file = null;
        this.httpResponseStatus = httpResponseStatus;
    }

    public static Response of(byte[] file, HttpResponseStatus httpResponseStatus) {
        return new Response(file, httpResponseStatus);
    }

    public static Response of(HttpResponseStatus httpResponseStatus) {
        return new Response(httpResponseStatus);
    }

    public byte[] getFile() {
        return file;
    }

    public HttpResponseStatus getStatus() {
        return httpResponseStatus;
    }

    @Override
    public String toString() {
        return "Response{" +
                "status='" + httpResponseStatus.getMessage() + '\'' +
                '}';
    }
}
