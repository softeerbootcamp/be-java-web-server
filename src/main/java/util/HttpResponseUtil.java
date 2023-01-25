package util;

import Response.ContentType;
import Response.HttpResponse;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.slf4j.Logger;

public class HttpResponseUtil {

    private static final Logger logger = LoggerFactory.getLogger(HttpResponseUtil.class);

    public static byte[] generateBytesBody(String requestPath) throws NullPointerException {
        Path path = FileIoUtil.mappingDirectoryPath(requestPath);
        try {
            byte[] body = Files.readAllBytes(path);
            return body;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
