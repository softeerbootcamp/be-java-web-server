package webserver.httpUtils;

import java.util.HashMap;
import java.util.Map;

public class Response {

    public static final String VERSION = "version";
    public static final String CODE = "code";
    public static final String TEXT = "text";

    private Map<String, String> resLine; // version, status code, status text
    private String resHeader;
    private byte resBody[];

    private String httpVersion;
    private String statusCode;
    private String statusText;


    public Response()
    {
        resLine = new HashMap<String, String>();
    }


    public Map<String, String> getResLine() {
        return resLine;
    }

    public void setResLine(Map<String, String> resLine) {
        this.resLine = resLine;
    }

    public String getResHeader() {
        return resHeader;
    }

    public void setResHeader(String resHeader) {
        this.resHeader = resHeader;
    }

    public byte[] getResBody() {
        return resBody;
    }

    public void setResBody(byte[] resBody) {
        this.resBody = resBody;
    }

}
