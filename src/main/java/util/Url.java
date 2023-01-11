package util;

public class Url {
    private final String url;

    private final UrlType urlType;

    public Url(String url, UrlType urlType) {
        this.url = url;
        this.urlType = urlType;
    }


    public String getUrl() {
        return url;
    }

    public UrlType getUrlType() {
        return urlType;
    }
}
