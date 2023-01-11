package webserver.domain.response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;
import webserver.domain.ContentType;
import webserver.domain.HttpBody;
import webserver.domain.HttpHeader;
import webserver.domain.StatusCodes;
import webserver.domain.request.RequestLine;
import webserver.exception.HttpRequestException;
import webserver.utils.HttpResponseUtil;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class Response {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private OutputStream out;
    private HttpBody body;
    private HttpHeader header;

    public Response(OutputStream out){
        this.out = out;
    }

    //Make Http response header for every error

    public void responseHeader(DataOutputStream dos, String errorCode, String errorMsg) throws IOException {
        dos.writeBytes("HTTP/1.1 " + errorCode + " " + errorMsg + " \r\n");
        dos.writeBytes(HttpResponseUtil.getHeaderContentFromHttp(header));
        dos.writeBytes("\r\n");
    }

    public void responseBody(DataOutputStream dos) throws IOException {
        String bodyContent = HttpResponseUtil.getBodyContentFromHttp(body);
        dos.writeBytes(bodyContent);
        dos.flush();
    }

    public void makeResponse(String statusCode, String msg)  {
        DataOutputStream dos = new DataOutputStream(out);
        try {
            responseHeader(dos, statusCode, msg);
            responseBody(dos);
        } catch (IOException e) {
            logger.debug(e.getMessage());
        }
    }
}
