package response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestResponseHandler;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ResponseSender {
    private DataOutputStream dos;
    private static final Logger logger = LoggerFactory.getLogger(RequestResponseHandler.class);

    public ResponseSender(OutputStream outputStream) {
        this.dos = new DataOutputStream(outputStream);
    }
    public void send(ResponseFactory responseFactory){
        try {
            ResponseAssembler responseAssembler = new ResponseAssembler(responseFactory);
            dos.writeBytes(responseAssembler.getAssembled()+System.lineSeparator());
            dos.write(responseAssembler.getBody(),0,responseAssembler.getBody().length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
