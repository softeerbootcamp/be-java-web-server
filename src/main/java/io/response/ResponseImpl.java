package io.response;

import enums.ContentType;
import enums.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;

public class ResponseImpl implements Response {
    private static final Logger logger = LoggerFactory.getLogger(ResponseImpl.class);
    private Status status;
    private ContentType contentType;
    private byte[] messageBody;

    private DataOutputStream out;

    public ResponseImpl(Status status, ContentType contentType, byte[] messageBody, DataOutputStream out) {
        this.status = status;
        this.contentType = contentType;
        this.messageBody = messageBody;
    }

    public Status getStatus() {
        return status;
    }

    public ContentType getContentType() {
        return contentType;
    }

    public byte[] getMessageBody() {
        return messageBody;
    }

    @Override
    public void send() {
        String msg = assembleMessage();
        try {
            out.writeBytes(msg);
            out.flush();
        } catch (IOException e) {
            logger.error("fail to response");
        }
    }

    private String assembleMessage() {
        return null;
    }
}
