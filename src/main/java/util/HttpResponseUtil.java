package util;

import Response.HttpResponse;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;

import org.slf4j.Logger;

public class HttpResponseUtil {

    private static final Logger logger = LoggerFactory.getLogger(HttpResponseUtil.class);
    public static HttpResponse response(OutputStream out, Path path) throws NoSuchFileException {
        byte[] body;
        try {
            body = Files.readAllBytes(path);
            DataOutputStream dos = new DataOutputStream(out);
            response200Header(dos, body.length);
            responseBody(dos, body);
            logger.debug("---------complete : " + path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new HttpResponse(body);
    }

    private static void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
    private static void response302Header(DataOutputStream dos, String redirect){
        try {
            dos.writeBytes("Location: " + redirect);
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: 0\r\n");
        }catch (IOException e){
            logger.error(e.getMessage());
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
