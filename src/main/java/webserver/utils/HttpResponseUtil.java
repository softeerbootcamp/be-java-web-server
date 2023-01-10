package webserver.utils;

import webserver.exception.HttpRequestException;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class HttpResponseUtil {

    //FIXME : RESPONSE 객체에 해당 내용들을 옮기기

    //Make Http response header for every error
    public static void responseHeader(DataOutputStream dos, String errorCode, String errorMsg, int lengthOfBodyContent) throws IOException {
        String responseLine = "HTTP/1.1 " + errorCode + " " + errorMsg + " \r\n";
        dos.writeBytes(responseLine);
        dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
        dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
        dos.writeBytes("\r\n");
    }

    public static void responseBody(DataOutputStream dos,byte[] body) throws IOException {
        dos.write(body, 0, body.length);
        dos.flush();
    }

    public static void makeResponse(byte[] body, OutputStream out, String statusCode, String msg) throws IOException {
        DataOutputStream dos = new DataOutputStream(out);
        responseHeader(dos, statusCode, msg, body.length);
        responseBody(dos, body);
    }
}
