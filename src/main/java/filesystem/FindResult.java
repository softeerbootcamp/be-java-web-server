package filesystem;

import enums.ContentType;
import enums.Status;

public class FindResult {

    private Status status = Status.OK;
    private ContentType contentType;
    private byte[] resource;

    public FindResult(String resourcePath, byte[] resource) {
        this.resource = resource;
        this.contentType = ContentType.find(resourcePath);

        if (resource.length == 0) {
            contentType = ContentType.HTML;
            status = Status.NOT_FOUND;
        }
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
