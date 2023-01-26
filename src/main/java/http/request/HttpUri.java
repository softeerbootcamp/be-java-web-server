package http.request;

import java.util.Optional;

public class HttpUri {

    private final String path;
    private final QueryParameters queryParameters;

    private HttpUri(
            String path,
            QueryParameters queryParameters
    ) {
        this.path = path;
        this.queryParameters = queryParameters;
    }

    public static HttpUri from(String uri) {
        String[] splitUri = uri.split("\\?");
        if (splitUri.length == 2) {
            return new HttpUri(splitUri[0], QueryParameters.from(splitUri[1]));
        }
        if (splitUri[0].equals("/") || splitUri[0].equals("")) {
            splitUri[0] = "/index.html";
        }
        return new HttpUri(splitUri[0], null);
    }

    public String getDetachControllerPath() {
        String[] split = path.split("/");
        return split[1];
    }

    public Optional<ResourceType> parseResourceType() {
        return ResourceType.findResourceType(path);
    }

    public String getPath() {
        return path;
    }

    public QueryParameters getQueryParameters() {
        return queryParameters;
    }

}
