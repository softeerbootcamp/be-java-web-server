package httpMock;

import httpMock.constants.ContentType;
import httpMock.constants.StatusCode;

public class CustomHttpErrorFactory {

    public static CustomHttpResponse BAD_REQUEST(String reason) {
        return CustomHttpResponse.of(
                StatusCode.BAD_REQUEST,
                ContentType.TEXT_PLAIN,
                CustomHttpResponse.EMPTY_HEADER,
                reason.getBytes()
        );
    }

    public static CustomHttpResponse NOT_FOUND() {
        return CustomHttpResponse.of(
                StatusCode.NOT_FOUND,
                ContentType.TEXT_PLAIN,
                CustomHttpResponse.EMPTY_HEADER,
                "요청하신 페이지를 찾을 수 없습니다.".getBytes()
        );
    }
}
