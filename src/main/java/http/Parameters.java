package http;

import java.util.Collections;
import java.util.Map;

import static utils.HttpRequestUtil.parseQuerystring;

public class Parameters {

    public final boolean isEmpty;
    private final Map<String, String> parameters;

    private Parameters(Map<String, String> parameters, boolean isEmpty) {
        this.parameters = parameters;
        this.isEmpty = isEmpty;
    }

    public static Parameters createEmptyParam() {
        return new Parameters(Collections.emptyMap(), true);
    }

    public static Parameters createParam(String queryString) {
        return new Parameters(parseQuerystring(queryString), false);
    }

    public Map<String, String> getParameters() {
        return Collections.unmodifiableMap(parameters);
    }

    public String getParameter(String key) {
        return parameters.get(key);
    }

    public boolean hasParameter() {
        return !isEmpty;
    }
}
