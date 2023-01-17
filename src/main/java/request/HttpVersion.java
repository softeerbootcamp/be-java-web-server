package request;

import java.util.Arrays;

public enum HttpVersion {
    ONE_POINT_ZERO("1.0"),
    ONE_POINT_ONE("1.1"),
    TWO_POINT_ZERO("2.0"),
    ELSE("");

    private String version;

    HttpVersion(String version) {
        this.version = version;
    }

    public static HttpVersion of(String version) {
        return Arrays.stream(values())
                .filter(httpVersion -> httpVersion.version.equals(version))
                .findAny()
                .orElse(ELSE);
    }

    public String getVersion() {
        return version;
    }
}

