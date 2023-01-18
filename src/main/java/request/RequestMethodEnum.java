package request;

import response.Response;

public enum RequestMethodEnum {
    GET("GET") {
        @Override
        public Response execute(RequestHandler handler, Request request) {
            return handler.doGet(request);
        }
    },
    POST("POST") {
        @Override
        public Response execute(RequestHandler handler, Request request) {
            return handler.doPost(request);
        }
    },
    PUT("PUT") {
        @Override
        public Response execute(RequestHandler handler, Request request) {
            return handler.doPut(request);
        }
    },
    DELETE("DELETE") {
        @Override
        public Response execute(RequestHandler handler, Request request) {
            return handler.doDelete(request);
        }
    }
    ;

    private String method;

    private RequestMethodEnum(String method) {
        this.method = method;
    }

    public String getMethod() {
        return method;
    }

    public abstract Response execute(RequestHandler handler, Request request);
}
