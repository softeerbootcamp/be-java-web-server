package io.response;

import enums.ContentType;
import enums.Status;

public class FindResult {

    private Status status;
    private ContentType contentType;
    private byte[] resource;

    public FindResult(Status status, ContentType contentType, byte[] resource) {
        this.status = status;
        this.contentType = contentType;
        this.resource = resource;
    }
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
