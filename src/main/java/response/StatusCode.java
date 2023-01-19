package response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public enum StatusCode {
    OK("200", "HTTP/1.1 200 OK"),
    Found("302", "HTTP/1.1 302 Found"),
    SeeOther("303", "HTTP/1.1 303 See Other"),
    CustomLogin("304","HTTP/1.1 304 Login Success"),
    TemporaryRedirect("307", "HTTP/1.1 303 Temporary Redirect"),
    Unauthorized("401", "HTTP/1.1 401 Unauthorized"),
    ELSE("", "");

    private final String code;
    private final String value;
    private static final Logger logger = LoggerFactory.getLogger(StatusCode.class);

    StatusCode(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public static StatusCode of(String code) {
        return Arrays.stream(values())
                .filter(statusLine -> statusLine.code.equals(code))
                .findAny()
                .orElse(ELSE);
    }

    public String getValue() {
        logger.debug("[StatusLine] value : {}",value);
        return value;
    }
}
