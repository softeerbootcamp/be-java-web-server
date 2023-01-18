package response;

import java.util.List;

public class ResponseAssembler {
    private static final String NEW_LINE = "\r\n";
    private static String assembled;
    private byte[] body;
    public ResponseAssembler(NewResponse newResponse) {
         assembled =
                newResponse.getResponseStatusLine().getResponseStatusLineInString() + NEW_LINE +
                newResponse.getResponseHeader().getHeaderLine();
         body = newResponse.getResponseBody().getBody();


    }

    public byte[] getBody() {
        return body;
    }

    public static String getAssembled() {
        return assembled;
    }

}
