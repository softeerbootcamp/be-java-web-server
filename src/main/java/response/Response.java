package response;

import enums.ContentTypeEnum;
import enums.ControllerTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestResponseHandler;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class Response {
    private static final Logger logger = LoggerFactory.getLogger(RequestResponseHandler.class);
    private static final String NEW_LINE = "\r\n";

    private DataOutputStream dos;

    public Response(OutputStream outputStream) {
        this.dos = new DataOutputStream(outputStream);

    }

    public void responseMaker(ControllerTypeEnum controllerTypeEnum, ContentTypeEnum contentTypeEnum,
                              int lengthOfBodyContent, String redirectUrl) {
        try {
            ResponseStatusLine responseStatusLine = new ResponseStatusLine(controllerTypeEnum);
            ResponseHeader responseHeader = new ResponseHeader(contentTypeEnum, lengthOfBodyContent);
            dos.writeBytes(responseStatusLine.getResponseStatusLine() + NEW_LINE);
            responseHeader.addHeaderIfRedirect(redirectUrl,responseStatusLine.getStatusCodeWithMessage());
            dos.writeBytes(responseHeader.getHeaderLine() + NEW_LINE);
            dos.writeBytes(NEW_LINE);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }


    public void responseBody(byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
