package dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utility.HttpStatusCode;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class HttpResponse {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);

    public final HttpStatusCode statusCode;
    public final String contentType;
    public final String resourcePath;
    private String generatedPage;
    private Map<String, String> header;

    public HttpResponse(String resourcePath, HttpStatusCode statusCode, String contentType, Map<String, String> header) {
        this.statusCode = statusCode;
        this.resourcePath = resourcePath;
        this.header = header;
        this.contentType = contentType;  // TODO: contentType = null일 때 e.g., woff 예외처리 필요
        this.generatedPage = "";
    }

    public HttpResponse(String resourcePath, HttpStatusCode statusCode, String contentType) {
        this(resourcePath, statusCode, contentType, new HashMap<>());
    }

    public HttpResponse(String resourcePath, HttpStatusCode statusCode, String contentType, Map<String, String> header, String generatedPage) {
        this(resourcePath, statusCode, contentType, header);
        this.generatedPage = generatedPage;
    }

    public DataOutputStream of(DataOutputStream dos) throws IOException {
        byte[] body = "".getBytes();

        if(!resourcePath.isBlank()) {
            try {
                body = Files.readAllBytes(new File(resourcePath).toPath());
            } catch (IOException e) {
                logger.error("cannot open file: " + e.getMessage());
            }
        }

        if(generatedPage.length() != 0) {
            logger.debug("동적 html");
            body = this.generatedPage.getBytes();
        }

        dos.writeBytes(String.format("%s %s \r\n", "HTTP/1.1", statusCode.toString()));
        dos.writeBytes("Content-Type: " + contentType + ";charset=utf-8 \r\n");
        dos.writeBytes("Content-Length: " + body.length + " \r\n");

        // StatusCode specific header
        for(Map.Entry<String, String> header : header.entrySet()) {
            String key = header.getKey();
            String val = header.getValue();

            dos.writeBytes(key + ": " + val + " \r\n");
        }
        dos.writeBytes("\r\n");

        dos.write(body, 0, body.length);

        return dos;
    }

    public static class HttpResponseBuilder {
        private HttpStatusCode statusCode;
        private String contentType;
        private String resourcePath;
        private String generatedPage;
        private Map<String, String> header;

        public HttpResponseBuilder setStatusCode(HttpStatusCode statusCode) {
            this.statusCode = statusCode;
            return this;
        }

        public HttpResponseBuilder setContentType(String contentType) {
            this.contentType = contentType;
            return this;
        }

        public HttpResponseBuilder setResourcePath(String resourcePath) {
            this.resourcePath = resourcePath;
            return this;
        }

        public HttpResponseBuilder setGeneratedPage(String generatedPage) {
            this.generatedPage = generatedPage;
            return this;
        }

        public HttpResponseBuilder setHeader(Map<String, String> header) {
            this.header = header;
            return this;
        }

        public HttpResponse build() {
            if(header == null)
                header = Map.of();
            if(generatedPage == null)
                generatedPage = "";
            return new HttpResponse(resourcePath, statusCode, contentType, header, generatedPage);
        }
    }
}
