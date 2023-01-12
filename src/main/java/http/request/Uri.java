package http.request;

import java.util.Map;

public class Uri {

    private static final String QUERY_DELIMITER = "?";
    private static final String QUERY_DELIMITER_REGEX = "\\?";


    private final String path;
    private final Parameter parameter;

    private Uri(String path, Parameter parameter) {
        this.path = path;
        this.parameter = parameter;
    }

    public static Uri from(String uri) {
        if (uri.contains(QUERY_DELIMITER)) {
            String[] tokens = uri.split(QUERY_DELIMITER_REGEX);
            String path = tokens[0];
            String query = tokens[1];

            return new Uri(path, Parameter.createParam(query));
        }
        return new Uri(uri, Parameter.createEmptyParam());
    }

    public Map<String, String> getParameters() {
        return parameter.getParameters();
    }

    public String getParameter(String key) {
        return parameter.getParameter(key);
    }

    public String getPath() {
        return path;
    }

    public boolean isEndsWith(String path) {
        return this.path.endsWith(path);
    }

}
