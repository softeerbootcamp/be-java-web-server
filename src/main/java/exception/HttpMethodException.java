package exception;

import utils.HttpMethod;

public class HttpMethodException extends RuntimeException {
    public HttpMethodException(HttpMethod methodName) {
        super(String.format("%s는 유효하지 않은 HttpMethod 입니다.", methodName));
    }
}
