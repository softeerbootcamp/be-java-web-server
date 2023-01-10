package webserver.utils;

import webserver.ErrorMsg;
import webserver.exception.HttpRequestError;
import webserver.exception.NoFileException;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import static webserver.utils.FilePathParser.getStaticFilesPath;
import static webserver.utils.FilePathParser.getTemplateFilesPath;

public class ResponseUtil {


    public static void response200Header(DataOutputStream dos, int lengthOfBodyContent) throws IOException {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
    }

    //Make Http response header for every error
    public static void responseErrorHeader(DataOutputStream dos, String errorCode, String errorMsg, int lengthOfBodyContent) throws IOException {
        String responseLine = "HTTP/1.1 " + errorCode + " " + errorMsg + " \r\n";
        dos.writeBytes(responseLine);
        dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
        dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
        dos.writeBytes("\r\n");
    }

    public static void responseBody(DataOutputStream dos, byte[] body) throws IOException {
        dos.write(body, 0, body.length);
        dos.flush();
    }

    public static void makeResponse(String fileName, OutputStream out) throws IOException {
        DataOutputStream dos = new DataOutputStream(out);
        try {
            byte[] body = FilePathParser.staticFileResolver(fileName);
            response200Header(dos, body.length);
            responseBody(dos, body);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (HttpRequestError e){
            byte[] body = "this is the error message".getBytes();
            responseErrorHeader(dos, e.getErrorCode(), e.getErrorMsg(), body.length);
            responseBody(dos, body);
        }
    }
}
