package webserver;

public class ViewResolver {

    public static String process(String url) {
        if (url.contains("css") || url.contains("js")) {
            return "src/main/resources/static" + url;
        }

        return "src/main/resources/templates" + url;
    }
}
