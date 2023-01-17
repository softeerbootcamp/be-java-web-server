package util;

import Request.StatusCode;
import Response.ContentType;
import Response.HttpResponse;
import Response.HttpResponseHeaders;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;

public class HttpResponseUtil {

    private static final Logger logger = LoggerFactory.getLogger(HttpResponseUtil.class);

    public static byte[] generateBody(String requestPath) throws NullPointerException {
        Path path = FileIoUtil.mappingDirectoryPath(requestPath);
        try {
            byte[] body = Files.readAllBytes(path);
            return body;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static HttpResponseHeaders generateHeaders(String requestPath, StatusCode statusCode, int length) {
        ContentType contentType = ContentType.PLAIN;
        if (statusCode.equals(StatusCode.OK)) {
            String ex = FileIoUtil.findExtension(requestPath);
            contentType = ContentType.valueOf(ex.toUpperCase());
        }

        Map<String, String> headers = new HashMap<>();          //headers
        headers.put("Content-Type", contentType.getContentText());
        headers.put("Content-Length", String.valueOf(length));
        return new HttpResponseHeaders(headers);
    }

    public static void sendResponse(DataOutputStream dos, HttpResponse httpResponse) {
        try {
            dos.writeBytes(httpResponse.toString());
            logger.debug("headers:\n" + httpResponse.toString());
            dos.writeBytes("\r\n");
            dos.write(httpResponse.getBody(), 0, httpResponse.getBody().length);
            dos.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
