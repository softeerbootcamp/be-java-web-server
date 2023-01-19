package request;

import request.RequestDataType;

public class Url {
    private  String url;

    private final RequestDataType requestDataType;

    public Url(String url, RequestDataType requestDataType) {
        this.url = url;
        this.requestDataType = requestDataType;
    }

    public void attachHtmlExtension() {
        if(!url.contains(".html"))
        this.url += ".html";
    }


    public String getUrl() {
        return url;
    }

    public RequestDataType getRequestDataType() {
        return requestDataType;
    }
}
