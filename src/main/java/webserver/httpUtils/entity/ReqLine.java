package webserver.httpUtils.entity;

public class ReqLine {
    private String method;
    private String query;
    private String version;

    // region getter setter
    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
    // endregion
}
