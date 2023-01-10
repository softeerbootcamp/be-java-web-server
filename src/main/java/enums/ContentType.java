package enums;

import java.util.Arrays;
import java.util.List;

public enum ContentType {
    HTML("text/html", List.of("html")),
    CSS("text/css", List.of("css")),
    JS("text/javascript", List.of("js")),
    PLAIN("plaintext", List.of());

    private String type;
    private List<String> extensions;

    ContentType(String type, List<String> extensions) {
        this.type = type;
        this.extensions = extensions;
    }

    public String getType() {
        return type;
    }

    public static ContentType find(String extension) {
        return Arrays.stream(ContentType.values())
                .filter(contentType -> contentType.extensions.contains(extension))
                .findFirst()
                .orElse(PLAIN);
    }
}
