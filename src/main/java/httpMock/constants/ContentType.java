package httpMock.constants;

import httpMock.CustomHttpResponse;

import java.util.List;

public enum ContentType {
    IMAGE_ICON("image/x-icon", List.of("ico")),
    TEXT_HTML("text/html", List.of("html")),
    TEXT_CSS("text/css", List.of("css")),
    TEXT_JAVASCRIPT("text/javascript", List.of("js")),
    TEXT_PLAIN("text/plain", List.of("txt")),
    TEXT_XML("text/xml", List.of("xml"));

    private final String contentType;
    private final List<String> fileTypes;


    ContentType(String contentType, List<String> fileType) {
        this.contentType = contentType;
        this.fileTypes = fileType;
    }

    public static ContentType getContentTypeByFileType(String fileType) {
        for (ContentType content : ContentType.values()) {
            if (content.fileTypes.contains(fileType))
                return content;
        }
        return TEXT_PLAIN;
    }


    public String getContentType() {
        return contentType + "; charset=" + CustomHttpResponse.CHARSET;
    }
}
