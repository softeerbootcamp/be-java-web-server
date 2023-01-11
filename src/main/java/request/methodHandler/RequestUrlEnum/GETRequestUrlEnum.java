package request.methodHandler.RequestUrlEnum;

import model.User;
import request.Request;
import request.RequestParser;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

public enum GETRequestUrlEnum {
    FORM("/user/create") {
        @Override
        public void handle(Request request, DataOutputStream dos) {
            User user = new User(RequestParser.parseGETQueryString(request.getResource()));
            System.out.println(user);
        }
    },
    TEMPLATE(".html, .ico") {
        @Override
        public void handle(Request request, DataOutputStream dos) {
            try {
                byte[] body = Files.readAllBytes(new File("src/main/resources/templates" + request.getResource()).toPath());
                response200Header(dos, body.length, request.getResourceFileContentType());
                GETRequestUrlEnum.responseBody(dos, body);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    },
    STATIC(".css, .eot, .svg, .ttf, .woff, .woff2, .png, .js") {
        @Override
        public void handle(Request request, DataOutputStream dos) {
            try {
                byte[] body = Files.readAllBytes(new File("src/main/resources/static" + request.getResource()).toPath());
                response200Header(dos, body.length, request.getResourceFileContentType());
                GETRequestUrlEnum.responseBody(dos, body);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };

    private String url;

    private GETRequestUrlEnum(String url) {
        this.url = url;
    }

    private String getUrl() {
        return url;
    }

    public List<String> getSupportingUrl(GETRequestUrlEnum urlEnum) {
        List<String> list = new ArrayList<>();
        StringTokenizer stringTokenizer = new StringTokenizer(urlEnum.getUrl(),", ");
        while(stringTokenizer.hasMoreTokens()) {
            list.add(stringTokenizer.nextToken());
        }
        return list;
    }

    private static void response200Header(DataOutputStream dos, int lengthOfBodyContent, String contentType) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: " + contentType + ";charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            //logger.error(e.getMessage());
        }
    }

    private static void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            //logger.error(e.getMessage());
        }
    }

    public abstract void handle(Request request, DataOutputStream dos);
}
