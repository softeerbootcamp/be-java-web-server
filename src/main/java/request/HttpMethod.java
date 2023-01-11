package request;

import request.methodHandler.*;

import java.io.DataOutputStream;
import java.io.IOException;

public enum HttpMethod {
    GET("GET") {
        @Override
        public void handle(Request request, DataOutputStream dos) throws IOException {
            HttpMethodHandler httpMethodHandler = new GETHandlerImpl(dos);
            httpMethodHandler.handle(request);
        }
    },
    POST("POST") {
        @Override
        public void handle(Request request, DataOutputStream dos) throws IOException {
            HttpMethodHandler httpMethodHandler = new POSTHandlerImpl(dos);
            httpMethodHandler.handle(request);
        }
    },
    PUT("PUT") {
        @Override
        public void handle(Request request, DataOutputStream dos) throws IOException {
            HttpMethodHandler httpMethodHandler = new PUTHandlerImpl(dos);
            httpMethodHandler.handle(request);
        }
    },
    DELETE("DELETE") {
        @Override
        public void handle(Request request, DataOutputStream dos) throws IOException {
            HttpMethodHandler httpMethodHandler = new DELETEHandlerImpl(dos);
            httpMethodHandler.handle(request);
        }
    };

    private String method;

    private HttpMethod(String method) {
        this.method = method;
    }

    public String getMethod() {
        return method;
    }

    public abstract void handle(Request request, DataOutputStream dos) throws IOException;
}
