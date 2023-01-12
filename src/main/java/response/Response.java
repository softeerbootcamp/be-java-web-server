package response;

import enums.ContentTypeEnum;
import enums.ControllerTypeEnum;
import enums.HeaderReferenceEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class Response {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final String NEW_LINE = "\r\n";

    private DataOutputStream dos;

    public Response(OutputStream outputStream) {
        this.dos = new DataOutputStream(outputStream);

    }

    public void responseMaker(ControllerTypeEnum controllerTypeEnum, ContentTypeEnum contentTypeEnum,
                              int lengthOfBodyContent, String redirectUrl) {
        try {
            ResponseStatusLine responseStatusLine = new ResponseStatusLine(controllerTypeEnum);
            dos.writeBytes(responseStatusLine.getResponseStatusLine() + NEW_LINE);
            dos.writeBytes(HeaderReferenceEnum.CONTENT_TYPE.getValueWithSpace() + contentTypeEnum.getValue() + ";charset=utf-8\r\n");
            dos.writeBytes(HeaderReferenceEnum.CONTENT_LENGTH.getValueWithSpace() + lengthOfBodyContent + NEW_LINE);
            if (responseStatusLine.getStatusCodeWithMessage().contains("302")) {
                dos.writeBytes(HeaderReferenceEnum.LOCATION.getValueWithSpace() + redirectUrl + NEW_LINE);
            }
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
