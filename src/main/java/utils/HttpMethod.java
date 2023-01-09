package utils;

public enum HttpMethod {
    GET(false),
    POST(true),
    PUT(true),
    DELETE(false),
    HEAD(false),
    CONNECT(false),
    OPTIONS(false),
    TRACE(false),
    PATCH(false);

    boolean hasBody;

    HttpMethod(boolean hasBody){
        this.hasBody = hasBody;
    }

    public boolean hasBody(){
        return hasBody;
    }
}
