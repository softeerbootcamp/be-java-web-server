package webserver.httpUtils;

import java.util.HashMap;
import java.util.Map;

public class Request {

    public final static String REQLINE_METHOD = "method";
    public final static String REQLINE_QUERY = "query";
    public final static String REQLINE_VERSION = "version";

    private Map<String, String> reqLine;
    private String reqHeader;
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

    public String getReqHeader() {
        return reqHeader;
    }

    public void setReqHeader(String reqHeader) {
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
