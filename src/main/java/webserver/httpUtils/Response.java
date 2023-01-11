package webserver.httpUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;

import java.io.*;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

import static webserver.Paths.STATIC_PATH;
import static webserver.Paths.TEMPLATE_PATH;

public class Response {
    private Logger logger;

    public static final String VERSION = "version";
    public static final String CODE = "code";
    public static final String TEXT = "text";

    private Map<String, String> resLine; // version, status code, status text
    private String resHeader;
    private byte resBody[];

    private String httpVersion;
    private String statusCode;
    private String statusText;


    public Response(Logger logger)
    {
        resLine = new HashMap<String, String>();
        this.logger = logger;
    }

    public void sendResponse(OutputStream out, String contentType) throws IOException {
        DataOutputStream dos = new DataOutputStream(out);

        responseHeader(dos, contentType);
        responseBody(dos, resBody);
    }

    private void responseHeader(DataOutputStream dos, String reqQuery) throws IOException {
        if(resLine.get(CODE).equals("302"))
        {
            response302Header(dos);
            logger.debug("302");
            return;
        }

        String contentType = Files.probeContentType(new File(reqQuery).toPath());
        resBody = Byte.urlToByte(reqQuery);
        response200Header(dos, resBody.length, contentType);
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent, String contentType) {
        try {
            dos.writeBytes(resLine.get(VERSION) + " " +resLine.get(CODE)+" "+resLine.get(TEXT)+"\r\n");
            dos.writeBytes("Content-Type: " + contentType + ";charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response302Header(DataOutputStream dos)
    {
        try {
            dos.writeBytes(resLine.get(VERSION) + " " +resLine.get(CODE)+" "+resLine.get(TEXT)+"\r\n");
            dos.writeBytes("Location: /index.html\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        }catch (IOException e) {
            logger.error(e.getMessage());
        }catch (NullPointerException e)
        {
            logger.debug("body가 null");
        }
    }

    public void probeResLine(String reqMethod, String reqQuery, String reqVersion)
    {
        resLine.put(VERSION, reqVersion);

        if(isSignUp(reqQuery))
        {
            resLine.put(CODE, "302");
            resLine.put(TEXT, "FOUND");
            return;
        }
        resLine.put(CODE, "200");
        resLine.put(TEXT, "OK");
    }

    private boolean isSignUp(String query)
    {
        if(query.contains("create?")) return true;
        return false;
    }
}

class Byte {
    public static byte[] urlToByte(String url) throws IOException {
        if (url.contains("html") || url.contains("favicon"))
            return Files.readAllBytes(new File(TEMPLATE_PATH.getPath() + url).toPath());
        else
            return Files.readAllBytes(new File(STATIC_PATH.getPath() + url).toPath());
    }
}
