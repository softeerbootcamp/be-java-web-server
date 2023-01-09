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

    public static HttpMethod getHttpMethod(String method) {
        for (HttpMethod mt : HttpMethod.values()){
            if (mt.name().equals(method))
                return mt;
        }
        throw new IllegalArgumentException("존재하지 않는 메서드입니다.");
    }
    public boolean hasBody(){
        return hasBody;
    }
}
