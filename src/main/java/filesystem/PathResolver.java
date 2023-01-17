package filesystem;

import enums.Extension;

import java.util.List;
import java.util.Map;

public class PathResolver {

    private static final String TEMPLATE_PATH = "src/main/resources/templates%s";
    private static final String STATIC_PATH = "src/main/resources/static%s";
    private static final String INDEX_HTML = "index.html";
    public static final String DOMAIN = "/";
    public static final String NOT_FOUND_HTML = "notfound.html";

    private PathResolver() {
    }

    // todo: String -> nio.Path로 변경
    private static final Map<List<String>, String> mappingInfo = Map.of(
            Extension.INDEX.getExtensions(), TEMPLATE_PATH + INDEX_HTML,
            Extension.TEMPLATE.getExtensions(), TEMPLATE_PATH,
            Extension.STATIC.getExtensions(), STATIC_PATH,
            Extension.ELSE.getExtensions(), NOT_FOUND_HTML
    );

    public static String parse(String url) {
        List<String> extension = mappingInfo.keySet().stream()
                .filter(keys -> keys.stream().anyMatch(url::endsWith)
                ).findAny()
                .orElse(Extension.ELSE.getExtensions());
        return String.format(mappingInfo.get(extension), url);
    }
}
