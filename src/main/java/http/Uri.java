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
        return queryParameters.isParameterExist();
    }

    public String getPath() {
        return path;
    }

    public QueryParameters getQueryParameters() {
        return queryParameters;
    }
}
