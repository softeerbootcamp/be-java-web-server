package util;

public class Url {
    private final String url;

    private final RequestDataType requestDataType;

    public Url(String url, RequestDataType requestDataType) {
        this.url = url;
        this.requestDataType = requestDataType;
    }


    public String getUrl() {
        return url;
    }

    public RequestDataType getRequestDataType() {
        return requestDataType;
    }
}
