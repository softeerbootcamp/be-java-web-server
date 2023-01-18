package webserver.httpUtils;

import webserver.httpUtils.entity.Body;
import webserver.httpUtils.entity.Header;
import webserver.httpUtils.entity.ReqLine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Request {
    private ReqLine reqLine;
    private Header header;
    private Body body;

    Request(){
        reqLine = new ReqLine();
        header = new Header();
        body = new Body();
    }

    // region GetterSetter
    public ReqLine getReqLine() {
        return reqLine;
    }

    public void setReqLine(String method, String query, String version) {
        reqLine.setMethod(method);
        reqLine.setQuery(query);
        reqLine.setVersion(version);
    }

    public Header getReqHeader() {
        return header;
    }

    public void setReqHeader(Map<String, String> reqHeader) {
        reqHeader.entrySet().forEach(
                elem -> {
                    header.insertKeyVal(elem.getKey(), elem.getValue());
                }
        );
    }

    public Body getReqBody() {
        return body;
    }

    public void setReqBody(String reqBody) {
        body.setContext(reqBody);
    }
    // endregion


}
