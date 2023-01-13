package request.GET;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.Request;
import webserver.WebServer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

public enum GETRequestUrlEnum {
    /*FORM("/user/create") {
        @Override
        public byte[] handle(Request request) {
            User user = new User(RequestParser.parseGETQueryString(request.getResource()));
            System.out.println(user);
            return new byte[0];
        }
    },*/
    TEMPLATE(".html .ico") {
        @Override
        public byte[] handle(Request request) {
            try {
                logger.debug("{}", "src/main/resources/templates" + request.getResource());
                return Files.readAllBytes(new File("src/main/resources/templates" + request.getResource()).toPath());
            } catch (IOException e) {
                logger.error("invalid request {}", request.getResource());
                return null;
            }
        }
    },
    STATIC(".css .eot .svg .ttf .woff .woff2 .png .js") {
        @Override
        public byte[] handle(Request request) {
            try {
                logger.debug("{}", "src/main/resources/static" + request.getResource());
                return Files.readAllBytes(new File("src/main/resources/static" + request.getResource()).toPath());
            } catch (IOException e) {
                logger.error("invalid request {}", request.getResource());
                return null;
            }
        }
    };

    private static Logger logger = LoggerFactory.getLogger(WebServer .class);

    private String url;

    private GETRequestUrlEnum(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public List<String> getSupportingFilePostfix(GETRequestUrlEnum urlEnum) {
        List<String> list = new ArrayList<>();
        StringTokenizer stringTokenizer = new StringTokenizer(urlEnum.getUrl()," ");
        while(stringTokenizer.hasMoreTokens()) {
            list.add(stringTokenizer.nextToken());
        }
        return list;
    }

    public abstract byte[] handle(Request request);
}
