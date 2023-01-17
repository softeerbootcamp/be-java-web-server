package filesystem;

import java.util.List;
import java.util.Map;

public class PathResolver {

    private static final String TEMPLATE_PATH = "src/main/resources/templates%s";
    private static final String STATIC_PATH = "src/main/resources/static%s";
    private static final String INDEX_HTML = "index.html";
    public static final String DOMAIN = "/";
    public static final String NOT_FOUND_HTML = "src/main/resources/templates/notfound.html";

    private PathResolver() {
    }

    // todo: String -> nio.Path로 변경
    private static final Map<Extension, String> mappingInfo = Map.of(
            Extension.INDEX, TEMPLATE_PATH + INDEX_HTML,
            Extension.TEMPLATE, TEMPLATE_PATH,
            Extension.STATIC, STATIC_PATH,
            Extension.ELSE, NOT_FOUND_HTML
    );

    public static String parse(String url) {
        Extension extension = mappingInfo.keySet().stream()
                .filter(keys -> keys.getExtensions().stream().anyMatch(url::endsWith)
                ).findAny()
                .orElse(Extension.ELSE);
        return String.format(mappingInfo.get(extension), url);
    }
}
