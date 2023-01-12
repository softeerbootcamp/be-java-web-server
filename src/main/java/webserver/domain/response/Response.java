package webserver.domain.response;

import webserver.domain.ContentType;
import webserver.domain.StatusCodes;
import webserver.utils.HttpResponseUtils;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class Response {

    private StatusCodes status;
    private Map<String, String> headerMaps = new HashMap<>();
    private byte[] body;
    public byte[] getBody() {
        return body;
    }

    public Map<String, String> getHeader() {
        return headerMaps;
    }

    public StatusCodes getStatusCode(){
        return this.status;
    }


    public void redirect(StatusCodes statusCode, byte[] bodyAsByte, ContentType contentType, String redirectUri) {
        status = statusCode;
        body = bodyAsByte;
        headerMaps.put("Content-Type" , contentType.getType());
        headerMaps.put("Content-Length", String.valueOf(body.length));
        headerMaps.put("Location", redirectUri);
    }

    public void ok(StatusCodes statusCode, byte[] bodyAsByte, ContentType contentType) {
        status = statusCode;
        body = bodyAsByte;
        headerMaps.put("Content-Type" , contentType.getType());
        headerMaps.put("Content-Length", String.valueOf(body.length));
    }

    public void error(StatusCodes statusCode) {
        status = statusCode;
    }
}
