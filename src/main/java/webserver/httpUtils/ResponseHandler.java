package webserver.httpUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.Paths;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class ResponseHandler {

    private static final Logger logger = LoggerFactory.getLogger(ResponseHandler.class);

    private Response res;
    public ResponseHandler(Response res) {
        this.res = res;
    }

    public void makeAndSendRes(OutputStream out, Map<String, String> reqLine) throws IOException {
        DataOutputStream dos = new DataOutputStream(out);

        makeResponseLine(reqLine);
        makeHeaderAndBody(reqLine);

        sendRes(dos);
    }

    private void makeResponseLine(Map<String, String> reqLine) {
        res.getResLine().put(Response.VERSION, reqLine.get(Request.VERSION));

        if (isSignUp(reqLine.get(Request.QUERY))) {
            res.getResLine().put(Response.CODE, "302");
            res.getResLine().put(Response.TEXT, "FOUND");
            return;
        }
        res.getResLine().put(Response.CODE, "200");
        res.getResLine().put(Response.TEXT, "OK");
    }

    private void makeHeaderAndBody(Map<String, String> reqLine) throws IOException {
        Map<String, String> header = new HashMap<>();
        if(res.getResLine().get(Response.CODE).equals("302"))
        {
            header.put("Location: ", Paths.HOME_PATH);
            res.setResHeader(header);
            return;
        }

        String reqQuery = reqLine.get(Request.QUERY);
        res.setResBody(Byte.urlToByte(reqQuery));

        String contentType = Files.probeContentType(new File(reqQuery).toPath());
        header.put("Content-Type: ", contentType + ";charset=utf-8");
        header.put("Content-Length: " , Integer.toString(res.getResBody().length));
        res.setResHeader(header);
    }

    private void sendRes(DataOutputStream dos) throws IOException
    {
        // resline
        dos.writeBytes(res.getResLine().get(Response.VERSION) + " " +
                res.getResLine().get(Response.CODE) + " " +
                res.getResLine().get(Response.TEXT) + "\r\n");
        // header
        res.getResHeader().entrySet().forEach(elem ->
                {
                    logger.debug(elem.getKey() + elem.getValue());
                    try {
                        dos.writeBytes(elem.getKey() + elem.getValue() + "\r\n");
                    } catch (IOException e) {
                        logger.error(e.getMessage());
                    }
                }
        );
        dos.writeBytes("\r\n");
        // body
        if(res.getResBody() != null)
            dos.write(res.getResBody(), 0, res.getResBody().length);
        dos.flush();
    }


    private boolean isSignUp(String query) {
        if (query.contains("create")) return true;
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
