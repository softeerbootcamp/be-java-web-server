package util;

import Request.HttpRequest;
import Response.HttpResponse;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;

public class HttpResponseUtil {

    private static final Logger logger = LoggerFactory.getLogger(HttpResponseUtil.class);

    public static byte[] generateBody(Path path) throws NullPointerException {
        try {
            byte[] body = Files.readAllBytes(path);
            return body;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Map<String, String> generateHeaders(String requestPath, StatusCode statusCode, int length) {
        ContentType contentType = ContentType.PLAIN;
        if (statusCode.equals(StatusCode.OK)) {
            String ex = FileIoUtil.findExtension(requestPath);
            contentType = ContentType.valueOf(ex.toUpperCase());
        }

        Map<String, String> headers = new LinkedHashMap<>();                //headers
        headers.put("Content-Type", contentType.getContentText());
        headers.put("Content-Length", String.valueOf(length));
        return headers;
    }

    public static void outResponse(DataOutputStream dos, HttpResponse httpResponse) {
        try {
            dos.writeBytes(httpResponse.getHeaders());
            logger.debug("headers:\n" + httpResponse.getHeaders());
            dos.writeBytes("\r\n");
            dos.write(httpResponse.getBody(), 0, httpResponse.getBody().length);
            dos.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
