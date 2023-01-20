package filesystem;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public enum Extension {

    INDEX(List.of("/")),
    TEMPLATE(List.of(".html", ".ico")),
    STATIC(List.of(".css", ".js", ".css", ".js", ".eot", ".svg", ".ttf", ".woff", ".woff2", ".png")),
    ELSE(List.of("*"));

    private List<String> extensions;

    Extension(List<String> extensions) {
        this.extensions = extensions;
    }

    public List<String> getExtensions() {
        return List.copyOf(extensions);
    }

    public static Boolean isStaticResource(String url) {
        return Arrays.stream(Extension.values())
                .map(Extension::getExtensions)
                .flatMap(Collection::stream)
                .distinct()
                .anyMatch(e -> url.endsWith(e));
    }
}
