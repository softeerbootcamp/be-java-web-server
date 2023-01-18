package util.error;

public class HttpsErrorMessage {
    public static final String NOT_VALID_HTTP_FORMAT = "[HTTP-ERROR]유효하지 않는 HTTP 형식입니다.";
    public static final String NOT_VALID_URL = "유효하지 않는 URL입니다.";
    public static final String NOT_FOUND_FILE = "해당 URL에 맞는 file을 찾지 못했습니다.";
    public static final String NOT_VALID_MATCHING_METHOD = "[REFLECTION-ERROR]컨트롤러 내에서 매칭되는 method가 없습니다.";
    public static final String NOT_VALID_PARAMETER_METHOD = "[REFLECTION-ERROR]매칭된 method 호출시 파라미터 Input이 잘못되었습니다.";
    public static final String NOT_LOGGED = "[LOGIN-ERROR]로그인 되지 않은사용자 입니다.";
}
