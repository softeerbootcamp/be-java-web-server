package request.method;

import request.method.DELETE.DELETEHandlerImpl;
import request.method.GET.GETHandlerImpl;
import request.method.POST.POSTHandlerImpl;
import request.method.PUT.PUTHandlerImpl;
import request.Request;
import response.Response;

import java.io.IOException;

public enum HttpMethod {
    GET("GET") {
        @Override
        public Response handle(Request request) throws IOException {
            HttpMethodHandler httpMethodHandler = GETHandlerImpl.getInstance();
            return httpMethodHandler.handle(request);
        }
    },
    POST("POST") {
        @Override
        public Response handle(Request request) throws IOException {
            HttpMethodHandler httpMethodHandler = POSTHandlerImpl.getInstance();
            return httpMethodHandler.handle(request);
        }
    },
    PUT("PUT") {
        @Override
        public Response handle(Request request) throws IOException {
            HttpMethodHandler httpMethodHandler = new PUTHandlerImpl();
            return httpMethodHandler.handle(request);
        }
    },
    DELETE("DELETE") {
        @Override
        public Response handle(Request request) throws IOException {
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

    public abstract Response handle(Request request) throws IOException;
}
