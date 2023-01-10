package enums;

import java.util.Arrays;
import java.util.List;

public enum ContentType {
    HTML("text/html;charset=utf-8", List.of("html")),
    CSS("text/css;charset=utf-8", List.of("css")),
    JS("text/javascript;charset=utf-8", List.of("js")),
    PLAIN("plaintext;charset=utf-8", List.of("*"));

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
