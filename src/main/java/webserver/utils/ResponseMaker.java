package webserver.utils;

import java.io.DataOutputStream;
import java.io.IOException;

public class ResponseMaker {

    public static void response200Header(DataOutputStream dos, int lengthOfBodyContent) throws IOException {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
    }

    public static void response404Header(DataOutputStream dos, int lengthOfBodyContent) throws IOException {
        dos.writeBytes("HTTP/1.1 404 NOT FOUND \r\n");
        dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
        dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
        dos.writeBytes("\r\n");
    }

    public static void responseBody(DataOutputStream dos, byte[] body) throws IOException {
        dos.write(body, 0, body.length);
        dos.flush();
    }
}
