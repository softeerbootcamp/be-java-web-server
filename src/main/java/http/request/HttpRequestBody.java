package http.request;

import http.Parameters;

import java.util.Map;

public class HttpRequestBody {
    private final Parameters parameter;

    private HttpRequestBody(Parameters parameter) {
        this.parameter = parameter;
    }

    public static HttpRequestBody createEmptyRequestBody() {
        Parameters emptyParam = Parameters.createEmptyParam();
        return new HttpRequestBody(emptyParam);
    }

    public static HttpRequestBody createRequestBody(String queryString) {
        Parameters param = Parameters.createParam(queryString);
        return new HttpRequestBody(param);
    }

    public Map<String, String> getParameters() {
        return parameter.getParameters();
    }

    public boolean isEmptyBody() {
        return parameter.isEmpty;
    }

}
