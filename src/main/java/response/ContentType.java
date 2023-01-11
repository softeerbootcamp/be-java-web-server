package response;

import java.util.Arrays;

public enum ContentType {
    HTML("html", "text/html"),
    CSS("css", "text/css"),
    JAVASCRIPT("js", "application/javascript");

    private final String extension;
    private final String value;

    ContentType(String extension, String value) {
        this.extension = extension;
        this.value = value;
    }

    public static String from(String path) {
        return Arrays.stream(values())
                .filter(type -> path.endsWith(type.extension))
                .findAny()
                .map(type -> type.value)
                .orElse(HTML.value);
    }
}
