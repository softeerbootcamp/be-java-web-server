package webserver;

public class ViewResolver {

    private static final String STATIC_RESOURCE_PATH = "src/main/resources/static";
    private static final String TEMPLATE_RESOUCE_PATH = "src/main/resources/templates";

    public static String process(String viewName) {
        if (viewName.contains("css") || viewName.contains("js")) {
            return STATIC_RESOURCE_PATH + viewName;
        }

        return TEMPLATE_RESOUCE_PATH + viewName;
    }
}
