package util;

import Request.HttpRequest;
import Response.HttpResponse;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.Objects;

import org.slf4j.Logger;

public class HttpResponseUtil {

    private static final Logger logger = LoggerFactory.getLogger(HttpResponseUtil.class);

    public static HttpResponse makeResponse(HttpRequest httpRequest, Path path){
        String protocol = httpRequest.getProtocol();
        if(Objects.nonNull(path)){
            byte[] body = generateBody(path);
            return new HttpResponse(body, "200", "OK", protocol);
        }
        return new HttpResponse(null, "404", "Not Found", protocol);
    }
    public static byte[] generateBody(Path path) throws NullPointerException{
        try {
            byte[] body = Files.readAllBytes(path);
            return body;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
