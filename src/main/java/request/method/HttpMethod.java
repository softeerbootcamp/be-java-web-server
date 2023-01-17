package request.method;

import request.method.GET.GETHandlerFactory;
import request.method.POST.POSTHandlerFactory;
import request.Request;
import response.Response;

import java.io.IOException;

public enum HttpMethod {
    GET("GET") {
        @Override
        public Response handle(Request request) throws IOException {
            return GETHandlerFactory.generateHandler(request).handle(request);
        }
    },
    POST("POST") {
        @Override
        public Response handle(Request request) throws IOException {
            return POSTHandlerFactory.generateHandler(request).handle(request);
        }
    },
    PUT("PUT") {
        @Override
        public Response handle(Request request) throws IOException {
            return null;
        }
    },
    DELETE("DELETE") {
        @Override
        public Response handle(Request request) throws IOException {
            return null;
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
