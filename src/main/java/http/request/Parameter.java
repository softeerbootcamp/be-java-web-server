package http.request;

import java.util.Collections;
import java.util.Map;

import static utils.HttpRequestUtil.parseQuerystring;

public class Parameter {

    public final boolean isEmpty;
    private final Map<String, String> parameters;

    private Parameter(Map<String, String> parameters, boolean isEmpty) {
        this.parameters = parameters;
        this.isEmpty = isEmpty;
    }

    public static Parameter createEmptyParam() {
        return new Parameter(Collections.emptyMap(), true);
    }

    public static Parameter createParam(String queryString) {
        return new Parameter(parseQuerystring(queryString), false);
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
