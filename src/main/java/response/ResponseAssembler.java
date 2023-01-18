package response;

public class ResponseAssembler {
    private static final String NEW_LINE = "\r\n";
    private static String assembled;
    private byte[] body;

    public ResponseAssembler(ResponseFactory responseFactory) {
        assembled =
                responseFactory.getResponseStatusLine().getResponseStatusLineInString() +
                        NEW_LINE +
                        responseFactory.getResponseHeader().getHeaderLine();
        body = responseFactory.getResponseBody().getBody();


    }

    public byte[] getBody() {
        return body;
    }

    public static String getAssembled() {
        return assembled;
    }

}
