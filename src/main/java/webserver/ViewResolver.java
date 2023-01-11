package webserver;

public class ViewResolver {

    private static final String STATIC_RESOURCE_PATH = "src/main/resources/static";
    private static final String TEMPLATE_RESOURCE_PATH = "src/main/resources/templates";
    private static final String HTML_FILE_EXTENSION = ".html";

    public static String process(String viewName) {
        if (viewName.endsWith(HTML_FILE_EXTENSION)) {
            return TEMPLATE_RESOURCE_PATH + viewName;
        }

        return STATIC_RESOURCE_PATH + viewName;
    }
}
