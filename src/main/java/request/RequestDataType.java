package request;

import java.util.Arrays;
import java.util.function.Function;

public enum RequestDataType {
    QUERY_STRING(".*\\?(.*)", value -> value.substring(0,value.indexOf("?"))),
    PATH_VARIABLE("^.*/([0-9]+)$", value -> value.substring(0,value.lastIndexOf("/"))),
    FILE(".*\\.(html|ico|css|js|tff|woff)$", value -> "file"),
    IN_BODY("", value -> value);

    private String regax;
    private Function<String, String> expression;

    RequestDataType(String regax, Function<String, String> expression) {
        this.regax = regax;
        this.expression = expression;
    }

    private String getRegax() {
        return regax;
    }

    public static RequestDataType getUrlType(String url) {
        return Arrays.stream(values())
                .filter(value -> url.matches(value.getRegax()))
                .findAny()
                .orElse(IN_BODY);
    }
    public String getMappingUrl(String path) {
        return expression.apply(path);
    }
}
