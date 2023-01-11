package webserver.domain.response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;
import webserver.domain.ContentType;
import webserver.domain.StatusCodes;
import webserver.utils.HttpResponseUtil;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class Response {

    private StatusCodes status;
    private ContentType contentType;
    private Map<String, String> headerMaps = new HashMap<>();
    private byte[] body;
    private HttpResponseUtil httpResponseUtil;

    public Response(OutputStream out){
        httpResponseUtil = new HttpResponseUtil(this, out);
    }

    //Make Http response header for every error
    public void setContentType(ContentType code){
        headerMaps.put("Content-Type" , code.getType());
    }

    public void setRedirection(String redirectUri){
        headerMaps.put("Location", redirectUri);
    }

    public void setBody(byte[] body){
        this.body= body;
        headerMaps.put("Content-Length", String.valueOf(body.length));
    }


    public byte[] getBody() {
        return body;
    }

    public Map<String, String> getHeader() {
        return headerMaps;
    }

    public void setStatusCode(StatusCodes statusCode) {
        this.status = statusCode;
    }

    public StatusCodes getStatusCode(){
        return this.status;
    }

    public void writeResponse(){
        httpResponseUtil.makeResponse();
    }


}
