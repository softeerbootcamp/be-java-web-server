package httpMock.constants;

import httpMock.CustomHttpResponse;

import java.util.List;

public enum ContentType {
    IMAGE_ICON("image/x-icon","ico"),
    TEXT_HTML("text/html","html"),
    TEXT_CSS("text/css","css"),
    TEXT_JAVASCRIPT("text/javascript","js"),
    TEXT_PLAIN("text/plain","txt"),
    TEXT_XML("text/xml","xml");

    private final String contentType;
    private final String fileType;


    ContentType(String contentType, String fileType) {
        this.contentType = contentType;
        this.fileType = fileType;
    }

    public static ContentType getContentTypeByFileType(String fileType) {
        for (ContentType content : ContentType.values()) {
            if (content.fileType.equals(fileType))
                return content;
        }
        return TEXT_PLAIN;
    }


    public String getContentType() {
        return contentType + "; charset=" + CustomHttpResponse.CHARSET;
    }
}
