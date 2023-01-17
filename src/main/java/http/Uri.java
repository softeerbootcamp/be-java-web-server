package http;

public class Uri {

    private final String path;
    private final QueryParameters queryParameters;

    private Uri(
            String path,
            QueryParameters queryParameters
    ) {
        this.path = path;
        this.queryParameters = queryParameters;
    }

    public static Uri from(String uri) {
        String[] splitUri = uri.split("\\?");
        if (splitUri.length == 2) {
            return new Uri(splitUri[0], QueryParameters.from(splitUri[1]));
        }
        return new Uri(splitUri[0], null);
    }

    public boolean isQueryParameterExist() {
        return queryParameters != null;
    }

    public boolean isEndWithResourceType() {
        return !parseResourceType().equals(ResourceType.NONE);
    }

    public String getDetachControllerPath() {
        String[] split = path.split("/");
        return split[1];
    }

    public ResourceType parseResourceType() {
        return ResourceType.findResourceType(path);
    }

    public String getPath() {
        return path;
    }

    public QueryParameters getQueryParameters() {
        return queryParameters;
    }
}
