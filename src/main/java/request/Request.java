package request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestResponseHandler;

import javax.sound.sampled.Line;
import java.io.BufferedReader;
import java.io.IOException;

public class Request {


    private static RequestLine requestLine = new RequestLine();
    private static RequestHeader requestHeader = new RequestHeader();
    private static RequestBody requestBody = new RequestBody();
    private static final Logger logger = LoggerFactory.getLogger(RequestResponseHandler.class);
    private static final String NEW_LINE = "\r\n";


    public Request(BufferedReader bufferedReader) throws IOException {
        setRequestLine(bufferedReader);
        setRequestHeader(bufferedReader);
        setRequestBody(bufferedReader);
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
            requestHeader.addHeaderLines(oneLine);
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



    public boolean isBodyExistsOrEOL(String oneLine) throws IOException {
        if (oneLine.equals("")) {
            return true;
        }
        return false;

    }

    public RequestLine getRequestLine() {
        return this.requestLine;
    }

    public static RequestHeader getRequestHeader() {
        return requestHeader;
    }

    public static RequestBody getRequestBody() {
        return requestBody;
    }
}
