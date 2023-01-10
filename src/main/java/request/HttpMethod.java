package request;

import request.methodHandler.*;

import java.io.IOException;

public enum HttpMethod {
    GET("GET") {
        @Override
        public byte[] handle(Request request) throws IOException {
            HttpMethodHandler httpMethodHandler = new GETHandlerImpl();
            return httpMethodHandler.handle(request);
        }
    },
    POST("POST") {
        @Override
        public byte[] handle(Request request) throws IOException {
            System.out.println("POST");
            HttpMethodHandler httpMethodHandler = new POSTHandlerImpl();
            return httpMethodHandler.handle(request);
        }
    },
    PUT("PUT") {
        @Override
        public byte[] handle(Request request) throws IOException {
            System.out.println("PUT");
            HttpMethodHandler httpMethodHandler = new PUTHandlerImpl();
            return httpMethodHandler.handle(request);
        }
    },
    DELETE("DELETE") {
        @Override
        public byte[] handle(Request request) throws IOException {
            System.out.println("DELETE");
            HttpMethodHandler httpMethodHandler = new DELETEHandlerImpl();
            return httpMethodHandler.handle(request);
        }
    };

    private String method;

    private HttpMethod(String method) {
        this.method = method;
    }

    public String getMethod() {
        return method;
    }

    public abstract byte[] handle(Request request) throws IOException;
}
