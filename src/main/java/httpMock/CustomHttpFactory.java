package httpMock;

import httpMock.constants.ContentType;
import httpMock.constants.StatusCode;

import java.util.HashMap;

public class CustomHttpFactory {

    public static CustomHttpResponse OK_HTML(String htmlFile){
        return CustomHttpResponse.of(
                StatusCode.OK,
                ContentType.TEXT_HTML,
                CustomHttpResponse.EMPTY_HEADER,
                htmlFile.getBytes()
        );
    }

    public static CustomHttpResponse REDIRECT(String location) {
        return CustomHttpResponse.of(
                StatusCode.FOUND,
                ContentType.TEXT_PLAIN,
                new HashMap<>() {{
                    put("Location", location);
                }},
                CustomHttpResponse.EMPTY_BODY
        );
    }

    public static CustomHttpResponse BAD_REQUEST(String reason) {
        return CustomHttpResponse.of(
                StatusCode.BAD_REQUEST,
                ContentType.TEXT_PLAIN,
                CustomHttpResponse.EMPTY_HEADER,
                reason.getBytes(CustomHttpResponse.CHARSET)
        );
    }

    public static CustomHttpResponse NOT_FOUND() {
        return CustomHttpResponse.of(
                StatusCode.NOT_FOUND,
                ContentType.TEXT_PLAIN,
                CustomHttpResponse.EMPTY_HEADER,
                "요청하신 페이지를 찾을 수 없습니다.".getBytes(CustomHttpResponse.CHARSET)
        );
    }

    public static CustomHttpResponse METHOD_NOT_ALLOWED() {
        return CustomHttpResponse.of(
                StatusCode.METHOD_NOT_ALLOWED,
                ContentType.TEXT_PLAIN,
                CustomHttpResponse.EMPTY_HEADER,
                "지원하지 않는 HTTP Method 입니다.".getBytes(CustomHttpResponse.CHARSET)
        );
    }


    public static CustomHttpResponse INTERNAL_ERROR(String errorMessage){
        return CustomHttpResponse.of(
                StatusCode.INTERNAL_ERROR,
                ContentType.TEXT_PLAIN,
                CustomHttpResponse.EMPTY_HEADER,
                errorMessage.getBytes(CustomHttpResponse.CHARSET)
        );
    }
}
