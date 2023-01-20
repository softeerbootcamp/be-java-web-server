package webserver.httpUtils;

import webserver.httpUtils.entity.Body;
import webserver.httpUtils.entity.Header;
import webserver.httpUtils.entity.ResLine;

import java.util.HashMap;
import java.util.Map;

public class Response {

    private ResLine resLine; // version, status code, status text
    private Header header;
    private Body body;


    public Response()
    {
        resLine = new ResLine();
        header = new Header();
        body = new Body();
    }


    public ResLine getResLine() {
        return resLine;
    }

    public void setResLine(String version, int code, String text) {
        resLine.setVersion(version);
        resLine.setStatCode(code);
        resLine.setStatText(text);
    }

    public Header getResHeader() {
        return header;
    }

    public void setResHeader(Map<String, String> resHeader) {
        resHeader.entrySet().forEach(
                elem -> {
                    header.insertKeyVal(elem.getKey(), elem.getValue());
                }
        );
    }

    public byte[] getResBody() {
        return body.getContext();
    }

    public void setResBody(byte[] resBody) {
        body.setContext(resBody);
    }

    // region builder pattern
    public Response withVersion(String version)
    {
        resLine.setVersion(version);
        return this;
    }

    public Response withStatCodeAndText(int code, String text)
    {
        resLine.setStatCode(code);
        resLine.setStatText(text);
        return this;
    }

    public Response withHeaderKeyVal(String key, String val)
    {
        header.insertKeyVal(key, val);
        return this;
    }

    public Response withBodyBytes(byte[] body)
    {
        this.body.setContext(body);
        return this;
    }

    public Response withBodyString(String body)
    {
        this.body.setContext(body);
        return this;
    }

    // endregion
}
