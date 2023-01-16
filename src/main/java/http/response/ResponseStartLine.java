package http.response;

import enums.Status;
import http.common.Protocol;

public class ResponseStartLine {
    private Protocol protocol = Protocol.HTTP_1_1;
    private Status status = Status.NOT_FOUND;

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return protocol.getValue() + " " + status.getCode() + " " + status.getMessage() + "\n";
    }
}

