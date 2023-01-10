package http;

import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toMap;

public class QueryParameters {

    private final Map<String, String> parameters;

    private QueryParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    public static QueryParameters from(String query) {
        return Stream.of(query.split("&"))
                .map(q -> q.split("="))
                .collect(collectingAndThen(toMap(q -> q[0], q -> q[1]), QueryParameters::new));
    }

    public String getParameter(String key) {
        return parameters.get(key);
    }


}
