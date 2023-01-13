package response;

import enums.ContentTypeEnum;
import enums.HeaderReferenceEnum;

public class ResponseHeader {
    private String headerLine;

    private static final String NEW_LINE = "\r\n";

    public ResponseHeader(ContentTypeEnum contentTypeEnum, String redirect, int lengthOfBodyContent) {
        setHeaderLine(contentTypeEnum, redirect, lengthOfBodyContent);
    }

    public String getHeaderLine() {
        return headerLine;
    }

    public void setHeaderLine(ContentTypeEnum contentTypeEnum, String redirect, int lengthOfBodyContent) {
        headerLine =
                contentTypeEnum.getValue() + NEW_LINE +
                        lengthOfBodyContent + NEW_LINE
        ;
    }

    public void addHeaderIfRedirect(String redirectUrl) {
        headerLine += HeaderReferenceEnum.LOCATION.getValueWithSpace() + redirectUrl + NEW_LINE;
    }
}
