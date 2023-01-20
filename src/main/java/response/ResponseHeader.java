package response;

import enums.ContentTypeEnum;
import enums.HeaderReferenceEnum;

public class ResponseHeader {
    private String headerLine;

    private static final String NEW_LINE = "\r\n";

    public ResponseHeader(ContentTypeEnum contentTypeEnum, int lengthOfBodyContent) {
        setHeaderLine(contentTypeEnum, lengthOfBodyContent);
    }

    public String getHeaderLine() {
        return headerLine;
    }

    public void setHeaderLine(ContentTypeEnum contentTypeEnum, int lengthOfBodyContent) {
        headerLine =
                HeaderReferenceEnum.CONTENT_TYPE.getValueWithSpace() + contentTypeEnum.getValue() + NEW_LINE +
                        HeaderReferenceEnum.CONTENT_LENGTH.getValueWithSpace() + lengthOfBodyContent + NEW_LINE
        ;
    }

    public void addCookieLine(String sid) {
        headerLine += HeaderReferenceEnum.SET_COOKIE.getValueWithSpace() +
                "sid=" + sid + "; Path=/" + NEW_LINE;
    }

    public void addHeaderLine(String addLine) {
        headerLine += addLine + NEW_LINE;
    }
}
