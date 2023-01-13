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

    public static ResourceType findResourceType(String path) {
        return Stream.of(values())
                .filter(resourceType -> path.endsWith(resourceType.getExtension()))
                .findFirst()
                .orElseThrow(() -> new ResourceTypeNotFoundException("지원하지 않는 정적 타입입니다."));
    }

    public String getExtension() {
        return extension;
    }

    public String getPath() {
        return path;
    }
}
