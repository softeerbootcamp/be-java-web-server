package webserver.httpUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.Paths;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.Map;

public class ResponseHandler {

    private static final Logger logger = LoggerFactory.getLogger(Response.class);
    private Response res;

    public ResponseHandler(Response res) {
        this.res = res;
    }

    public void sendResponse(OutputStream out, String reqQuery) throws IOException {
        DataOutputStream dos = new DataOutputStream(out);

        responseHeader(dos, reqQuery);
        responseBody(dos, res.getResBody());
    }

    private void responseHeader(DataOutputStream dos, String reqQuery) throws IOException {
        if (res.getResLine().get(Response.CODE).equals("302")) {
            response302Header(dos);
            logger.debug("302");
            return;
        }

        String contentType = Files.probeContentType(new File(reqQuery).toPath());
        res.setResBody(Byte.urlToByte(reqQuery));
        response200Header(dos, res.getResBody().length, contentType);
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent, String contentType) {
        try {
            dos.writeBytes(res.getResLine().get(Response.VERSION) + " " +
                    res.getResLine().get(Response.CODE) + " " +
                    res.getResLine().get(Response.TEXT) + "\r\n");
            dos.writeBytes("Content-Type: " + contentType + ";charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response302Header(DataOutputStream dos) {
        try {
            dos.writeBytes(res.getResLine().get(Response.VERSION) + " " +
                    res.getResLine().get(Response.CODE) + " " +
                    res.getResLine().get(Response.TEXT) + "\r\n");
            dos.writeBytes("Location: " + Paths.HOME_PATH + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        } catch (NullPointerException e) {
            logger.debug("body가 null");
        }
    }

    public void probeResLine(Map<String, String> reqLine) {
        res.getResLine().put(Response.VERSION, reqLine.get(Request.REQLINE_VERSION));

        if (isSignUp(reqLine.get(Request.REQLINE_QUERY))) {
            res.getResLine().put(Response.CODE, "302");
            res.getResLine().put(Response.TEXT, "FOUND");
            return;
        }
        res.getResLine().put(Response.CODE, "200");
        res.getResLine().put(Response.TEXT, "OK");
    }

    private boolean isSignUp(String query) {
        if (query.contains("create?")) return true;
        return false;
    }
}

class Byte {
    public static byte[] urlToByte(String url) throws IOException {
        if (url.contains("html") || url.contains("favicon"))
            return Files.readAllBytes(new File(Paths.TEMPLATE_PATH + url).toPath());
        else
            return Files.readAllBytes(new File(Paths.STATIC_PATH + url).toPath());
    }
}
