package request;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public enum HttpMethod {
    GET("GET") {
        @Override
        public byte[] handle(Request request) throws IOException {
            return handleGET(request);
        }
    },
    POST("POST") {
        @Override
        public byte[] handle(Request request) {
            return new byte[0];
        }
    },
    PUT("PUT") {
        @Override
        public byte[] handle(Request request) {
            return new byte[0];
        }
    },
    DELETE("DELETE") {
        @Override
        public byte[] handle(Request request) {
            return new byte[0];
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

    private static byte[] handleGET(Request request) throws IOException {
        return Files.readAllBytes(new File("src/main/resources/"
                + (request.getResource().contains(".html") || request.getResource().contains(".ico") ? "templates" : "static") + request.getResource()).toPath());
    }
}
