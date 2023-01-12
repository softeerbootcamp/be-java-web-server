package webserver.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;
import webserver.domain.StatusCodes;
import webserver.domain.response.Response;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import static webserver.utils.CommonUtils.mapToStringSplitWithNewLine;

public class HttpResponseUtils {

    private Response response;
    private DataOutputStream dos;
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    public HttpResponseUtils(Response response, OutputStream out){
        this.response = response;
        this.dos = new DataOutputStream(out);
    }

    public void writeRequestLine(StatusCodes code) throws IOException {
        dos.writeBytes("HTTP/1.1 " + code.getStatusCode() + " " + code.getStatusMsg() + " \r\n");
    }

    public void writeHeader() throws IOException {
        dos.writeBytes(mapToStringSplitWithNewLine(response.getHeader()));
        dos.writeBytes("\r\n");
    }

    public void writeBody() throws IOException {
        dos.write(response.getBody(), 0, response.getBody().length);
        dos.flush();
    }

    public void makeResponse()
    {
        try {
            writeRequestLine(response.getStatusCode());
            writeHeader();
            writeBody();
        } catch (IOException e) {
            logger.debug(e.getMessage());
        }
    }



}
