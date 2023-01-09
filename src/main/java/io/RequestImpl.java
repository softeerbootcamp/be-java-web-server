package io;

public class RequestImpl implements Request {

    private String url;

    public RequestImpl(String msg) {
        url = parseUrl(msg);
    }

    private String parseUrl(String msg) {
        String[] split = msg.split("\n\r");
        return split[0].split(" ")[1];
    }

    @Override
    public String getUrl() {
        return url;
    }
}
