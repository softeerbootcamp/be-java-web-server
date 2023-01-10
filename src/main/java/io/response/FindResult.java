package io.response;

import enums.ContentType;
import enums.Status;

public class FindResult {

    private static final String SERVER_ERROR_MSG = "SERVER_ERROR";
    private Status status = Status.NOT_FOUND;
    private ContentType contentType = ContentType.HTML;
    private byte[] resource = SERVER_ERROR_MSG.getBytes();

    public void update(Status status, ContentType contentType, byte[] resource) {
        this.status = status;
        this.contentType = contentType;
        this.resource = resource;
    }

    public Status getStatus() {
        return status;
    }

    public ContentType getContentType() {
        return contentType;
    }

    public byte[] getResource() {
        return resource;
    }
}
