package response;

import java.util.List;

public class ResponseAssembler {
    private static final String NEW_LINE = "\r\n";
    private static String assembled;
    private byte[] body;
    public ResponseAssembler(NewResponse newResponse) {
         assembled =
                newResponse.getResponseStatusLine().getResponseStatusLine() + NEW_LINE +
                newResponse.getResponseHeader().getHeaderLine() +
                ResponseAdderListToString(newResponse.getResponseAdder());
         body = newResponse.getResponseBody().getBody();


    }

    public byte[] getBody() {
        return body;
    }

    public static String getAssembled() {
        return assembled;
    }

    public String ResponseAdderListToString(List<String> responseAdder) {
        String result = "";
        for (String line : responseAdder) {
            result += (line + NEW_LINE);
        }
        return result;
    }
}
