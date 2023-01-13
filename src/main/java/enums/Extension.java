package enums;

import java.util.List;

public enum Extension {

    INDEX(List.of("/")),
    TEMPLATE(List.of(".html")),
    STATIC(List.of(".css", ".js", ".css", ".js", ".eot", ".svg", ".ttf", ".woff", ".woff2", ".png")),
    ELSE(List.of("*"));

    private List<String> extensions;

    Extension(List<String> extensions) {
        this.extensions = extensions;
    }

    public List<String> getExtensions() {
        return List.copyOf(extensions);
    }
}
