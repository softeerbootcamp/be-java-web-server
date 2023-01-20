package http.response;

import java.util.HashMap;
import java.util.Map;

public class HttpResponseFactory {

    public static HttpResponse OK(String contentType, Map<String, String> header, byte[] body) {
        return HttpResponse.of(
                HttpStatus.OK,
                contentType,
                header,
                body
        );
    }

    public static HttpResponse OK(String contentType, byte[] body) {
        return HttpResponse.of(
                HttpStatus.OK,
                contentType,
                new HashMap<>(),
                body
        );
    }

    public static HttpResponse FOUND(String location) {
        return HttpResponse.of(
                HttpStatus.FOUND,
                "",
                new HashMap<>() {{
                    put("Location", location);
                }},
                "".getBytes()
        );
    }

    public static HttpResponse NOT_FOUND(String message) {
        return HttpResponse.of(
                HttpStatus.NOT_FOUND,
                "",
                new HashMap<>(),
                message.getBytes()
        );
    }
}
