package request.method.GET;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.Request;
import response.HttpResponseStatus;
import response.Response;
import webserver.WebServer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

public enum GETFileRequestEnum {
    TEMPLATE(".html .ico") {
        @Override
        public Response handle(Request request) {
            try {
                logger.debug("{}", "src/main/resources/templates" + request.getResource());
                return Response.of(Files.readAllBytes(new File("src/main/resources/templates" + request.getResource()).toPath()),
                        HttpResponseStatus.OK.getMessage(), HttpResponseStatus.OK.getCode());
            } catch (IOException e) {
                logger.error("invalid request {}", request.getResource());
                return Response.of(HttpResponseStatus.NOT_FOUND.getMessage(), HttpResponseStatus.NOT_FOUND.getCode());
            }
        }
    },
    STATIC(".css .eot .svg .ttf .woff .woff2 .png .js") {
        @Override
        public Response handle(Request request) {
            try {
                logger.debug("{}", "src/main/resources/static" + request.getResource());
                return Response.of(Files.readAllBytes(new File("src/main/resources/static" + request.getResource()).toPath()),
                        HttpResponseStatus.OK.getMessage(), HttpResponseStatus.OK.getCode());
            } catch (IOException e) {
                logger.error("invalid request {}", request.getResource());
                return Response.of(HttpResponseStatus.NOT_FOUND.getMessage(), HttpResponseStatus.NOT_FOUND.getCode());
            }
        }
    };

    private static Logger logger = LoggerFactory.getLogger(WebServer .class);

    private final String url;

    private GETFileRequestEnum(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public List<String> getSupportingFilePostfix(GETFileRequestEnum urlEnum) {
        List<String> list = new ArrayList<>();
        StringTokenizer stringTokenizer = new StringTokenizer(urlEnum.getUrl()," ");
        while(stringTokenizer.hasMoreTokens()) {
            list.add(stringTokenizer.nextToken());
        }
        return list;
    }

    public abstract Response handle(Request request);
}
