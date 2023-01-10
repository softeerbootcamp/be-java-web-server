package enums;

import java.util.Arrays;
import java.util.List;

public enum ContentType {
    HTML("text/html", List.of("html")),
    CSS("text/css", List.of("css")),
    JS("text/javascript", List.of("js")),
    PLAIN("plaintext", List.of("*"));

    private String type;
    private List<String> extensions;

    ContentType(String type, List<String> extensions) {
        this.type = type;
        this.extensions = extensions;
    }

    public String getType() {
        return type;
    }

    public static ContentType find(String url) {
        return Arrays.stream(ContentType.values())
                .filter(contentType -> contentType.extensions.contains(getExtension(url)))
                .findFirst()
                .orElse(PLAIN);
    }

    private static String getExtension(String url) {
        return url.split("\\.")[1];
    }
}
