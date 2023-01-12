package io.response.startline;

import enums.Status;
import io.request.startLine.Protocol;

public class RequestStartLine {
    private Protocol protocol = Protocol.HTTP_1_1;
    private Status status = Status.BAD_REQUEST;

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return protocol.getValue() + " " + status.getCode() + " " + status.getMessage() + "\n\r";
    }
}

