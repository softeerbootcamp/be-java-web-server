package util;

import model.general.ContentType;
import model.general.Header;

import java.util.HashMap;
import java.util.Map;

public class HeaderUtils {
    private static final Map<Header, String> headers = new HashMap<>();

    public static Map<Header, String> response200Header(ContentType contentType, int messageBodyLength) {
        Map<Header, String> headers = new HashMap<>();
        headers.put(Header.from("Content-Type"), contentType.getContentTypeValue() + ";charset=utf-8");
        headers.put(Header.from("Content-Length"), Integer.toString(messageBodyLength));

        return headers;
    }

    public static Map<Header, String> responseRedirectIndexHtmlHeader() {
        headers.put(Header.from("Location"), "/index.html");

        return headers;
    }

    public static Map<Header, String> responseLoginSuccessHeader(String sessionId) {
        headers.put(Header.from("Location"), "/index.html");
        headers.put(Header.from("Set-Cookie"), "sid=" + sessionId + "; Path=/");

        return headers;
    }

    public static Map<Header, String> responseLoginFailHeader() {
        headers.put(Header.from("Location"), " /user/login_failed.html");

        return headers;
    }

    public static Map<Header, String> responseRedirectLoginHtmlHeader() {
        headers.put(Header.from("Location"), " /user/login.html");

        return headers;
    }
}
