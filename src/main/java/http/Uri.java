package http;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

public class Uri {

    private final String path;
    private final Map<String, String> query;

    public Uri(
            String uri
    ) {
        Map<String, String> query1 = new HashMap<>();
        String[] splitUri = uri.split("\\?");
        this.path = splitUri[0];

        if(splitUri.length == 2) {
            String[] queries = splitUri[1].split("&");
            query1 = Stream.of(queries)
                    .map(query -> query.split("="))
                    .collect(toMap(query -> query[0], query -> query[1]));
        }

        this.query = query1;
    }

    public String getPath() {
        return path;
    }

    public Map<String, String> getQuery() {
        return query;
    }
}
