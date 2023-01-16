package httpMock.constants;

public enum HttpMethod {
    GET("GET"),
    POST("POST"),
    DELETE("DELETE");

    private String methodName;

    HttpMethod(String initMethodName){
        methodName = initMethodName;
    }

    public String getName(){
        return methodName;
    }

    @Override
    public String toString(){
        return methodName;
    }
}
