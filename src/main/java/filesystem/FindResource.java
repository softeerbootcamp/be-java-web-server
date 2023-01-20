package filesystem;

import enums.ContentType;
import enums.Status;

public class FindResource {

    private Status status = Status.OK;
    private ContentType contentType;
    private byte[] resource;

    public FindResource(String resourcePath, byte[] resource) {
        this.resource = resource;
        this.contentType = ContentType.find(resourcePath);
        if (resourcePath.equals(PathResolver.NOT_FOUND_HTML)) {
            this.status = Status.NOT_FOUND;
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
