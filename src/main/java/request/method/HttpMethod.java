package request.method;

import request.method.DELETE.DELETEHandlerImpl;
import request.method.GET.GETHandlerImpl;
import request.method.POST.POSTHandlerImpl;
import request.method.PUT.PUTHandlerImpl;
import request.Request;

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
            HttpMethodHandler httpMethodHandler = new POSTHandlerImpl();
            return httpMethodHandler.handle(request);
        }
    },
    PUT("PUT") {
        @Override
        public byte[] handle(Request request) throws IOException {
            HttpMethodHandler httpMethodHandler = new PUTHandlerImpl();
            return httpMethodHandler.handle(request);
        }
    },
    DELETE("DELETE") {
        @Override
        public byte[] handle(Request request) throws IOException {
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
