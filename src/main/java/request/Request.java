package request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import session.HttpCookie;
import session.HttpSessions;
import webserver.RequestResponseHandler;

import java.io.BufferedReader;
import java.io.IOException;

public class Request {


    private RequestLine requestLine;
    private RequestHeader requestHeader;
    private RequestBody requestBody;
    private HttpCookie httpCookie;
    private static final Logger logger = LoggerFactory.getLogger(RequestResponseHandler.class);
    private static final String NEW_LINE = "\r\n";


    public Request(BufferedReader bufferedReader) throws IOException {
        setRequestLine(bufferedReader);
        setRequestHeader(bufferedReader);
        setHttpCookie(requestHeader);
        setRequestBody(bufferedReader);
        if (requestLine.getMETHOD().equals(null)) {
            throw new NullPointerException("아무런 요청이 없습니다!!");
        }
    }

    public void setRequestLine(BufferedReader bufferedReader) throws IOException {
        requestLine = new RequestLine();
        requestLine.addRequestLine(bufferedReader.readLine());
    }

    public void setRequestHeader(BufferedReader bufferedReader) throws IOException {
        requestHeader = new RequestHeader();
        while (true) {
            String oneLine = bufferedReader.readLine();
            logger.debug("set request header line : " + oneLine);
            if (isBodyExistsOrEOL(oneLine)) {
                break;
            }
            String[] headerMap = oneLine.split(":");
            requestHeader.addHeaderMap(headerMap[0], headerMap[1]);
        }

    }

    public void setRequestBody(BufferedReader bufferedReader) throws IOException {
        requestBody = new RequestBody();
        String fullLine = "";
        while (bufferedReader.ready()) {
            int line = bufferedReader.read();
            fullLine += (char) line;
        }
        logger.debug("requestBody : " + fullLine);
        requestBody.addBodyLines(fullLine);
    }

    public void setHttpCookie(RequestHeader requestHeader) {
        // 쿠키가 없을때
        if (!requestHeader.isHeaderMapContains("Cookie")) {
            this.httpCookie = new HttpCookie(null);
            return;
        }
        // 쿠키가 존재
        this.httpCookie = new HttpCookie(requestHeader.getHeaderValueByKey("Cookie").split("=")[1]);
        // 해당 쿠키가 유효한지 확인
        if(!HttpSessions.cookieValidationCheck(this.httpCookie)){
            this.httpCookie.cleanCookie();
        }
    }


    public HttpCookie getHttpCookie() {
        return httpCookie;
    }

    public boolean isBodyExistsOrEOL(String oneLine) throws IOException {
        if (oneLine.equals("")) {
            return true;
        }
        return false;

    }

    public RequestLine getRequestLine() {
        return this.requestLine;
    }

    public RequestHeader getRequestHeader() {
        return requestHeader;
    }

    public RequestBody getRequestBody() {
        return requestBody;
    }
}
