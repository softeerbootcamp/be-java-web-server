package response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestResponseHandler;

import java.io.DataOutputStream;
import java.io.IOException;

public class ResponseSender {
    private DataOutputStream dos;
    private static final Logger logger = LoggerFactory.getLogger(RequestResponseHandler.class);

    public ResponseSender() {

    }
    public void send(NewResponse newResponse){
        try {
            dos.writeBytes(newResponse.ResponseAssembler());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
