package http.request;

import http.Parameter;

import java.util.Map;

public class HttpRequestBody {
    private final Parameter parameter;

    private HttpRequestBody(Parameter parameter) {
        this.parameter = parameter;
    }

    public static HttpRequestBody createRequestBody(String queryString) {
        Parameter param = Parameter.createParam(queryString);
        return new HttpRequestBody(param);
    }

    public Map<String, String> getParameters() {
        return parameter.getParameters();
    }

    public boolean isEmptyBody() {
        return parameter.isEmpty;
    }

}
