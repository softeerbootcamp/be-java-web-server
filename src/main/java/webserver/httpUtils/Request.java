package webserver.httpUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Request {

    public final static String METHOD = "method";
    public final static String QUERY = "query";
    public final static String VERSION = "version";

    private Map<String, String> reqLine;
    private Map<String, String> reqHeader;
    private String reqBody;

    Request(){
        reqLine = new HashMap<String, String>();
    }

    // region GetterSetter
    public Map<String, String> getReqLine() {
        return reqLine;
    }

    public void setReqLine(Map<String, String> reqLine) {
        this.reqLine = reqLine;
    }

    public Map<String, String> getReqHeader() {
        return reqHeader;
    }

    public void setReqHeader(Map<String, String> reqHeader) {
        this.reqHeader = reqHeader;
    }

    public String getReqBody() {
        return reqBody;
    }

    public void setReqBody(String reqBody) {
        this.reqBody = reqBody;
    }
    // endregion
}
