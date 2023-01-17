package http.request;

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
        return new HttpUri(splitUri[0], null);
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

    public String getDetachServicePath() {
        String[] split = path.split("/");
        return split[2];
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
