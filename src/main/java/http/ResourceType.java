package http;

import java.util.stream.Stream;

public enum ResourceType {

    HTML(".html", "/templates"),
    ICO(".ico", "/templates"),
    XML(".xml", "/templates"),
    JS(".js", "/static"),
    PNG(".png", "/static"),
    WOFF2(".woff2", "/static"),
    WOFF(".woff", "/static"),
    TTF(".ttf", "/static"),
    SVG(".svg", "/static"),
    EOT(".eot", "/static"),
    CSS(".css", "/static");


    private final String extension;
    private final String path;

    ResourceType(String extension,
                 String path
    ) {
        this.extension = extension;
        this.path = path;
    }

    public static ResourceType findResourceType(final Uri uri) {
        return Stream.of(values())
                .filter(resourceType -> uri.getPath().endsWith(resourceType.getExtension()))
                .findFirst()
                .orElse(null);
    }

    public String getExtension() {
        return extension;
    }

    public String getPath() {
        return path;
    }
}
