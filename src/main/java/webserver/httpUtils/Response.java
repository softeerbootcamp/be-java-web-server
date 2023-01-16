package webserver.httpUtils;

import java.util.HashMap;
import java.util.Map;

public class Response {

    public static final String VERSION = "version";
    public static final String CODE = "code";
    public static final String TEXT = "text";

    private Map<String, String> resLine; // version, status code, status text
    private Map<String, String> resHeader;
    private byte resBody[];


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

    public Map<String, String> getResHeader() {
        return resHeader;
    }

    public void setResHeader(Map<String, String> resHeader) {
        this.resHeader = resHeader;
    }

    public byte[] getResBody() {
        return resBody;
    }

    public void setResBody(byte[] resBody) {
        this.resBody = resBody;
    }

}
