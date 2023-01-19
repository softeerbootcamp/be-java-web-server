package response;

import request.HttpVersion;

import java.util.HashMap;
import java.util.Map;

public class CreateResponse {
    private static final String DELIMITER = "\\.";

    private final StatusLine statusLine;
    private Map<String, String> fields;
    private byte[] body;

    public CreateResponse(StatusCode statusCode) {
        this.statusLine = new StatusLine(HttpVersion.ONE_POINT_ONE, statusCode);
        this.fields = new HashMap<>();
        this.body = new byte[0];
    }

    public static CreateResponse ok(){
        return new CreateResponse(StatusCode.OK);
    }
    
}
