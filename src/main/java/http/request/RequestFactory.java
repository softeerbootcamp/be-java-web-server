package http.request;

import http.common.Body;
import http.common.Method;
import http.response.enums.ResponseAttribute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class RequestFactory {
    private final Logger logger = LoggerFactory.getLogger(RequestFactory.class);

    public HttpRequest create(InputStream in) throws IOException {
        BufferedReader bufferedReader = getBufferedReader(in);

        RequestStartLine startLine = new RequestStartLine(bufferedReader.readLine());
        Header header = new Header(getHeader(bufferedReader));

        if (startLine.getMethod() == Method.GET) {
            return new HttpRequest(startLine, header, new RequestParameter(startLine));
        }
        return getNewPostRequest(startLine, header, bufferedReader);
    }

    private HttpRequest getNewPostRequest(RequestStartLine startLine, Header header, BufferedReader bufferedReader) throws IOException {
        Integer contentLength = Integer.parseInt(header.getAttribute(ResponseAttribute.CONTENT_LENGTH));
        Body body = new Body(getBody(contentLength, bufferedReader));
        return new HttpRequest(startLine, header, new RequestParameter(body));
    }

    private byte[] getBody(Integer contentLength, BufferedReader bufferedReader) throws IOException {
        byte[] bytes = new byte[contentLength];
        for (int i = 0; i < contentLength; i++) {
            bytes[i] = (byte) bufferedReader.read();
        }
        return bytes;
    }

    private String getHeader(BufferedReader bufferedReader) {
        String msg = "";
        String line;
        try {
            while (!(line = bufferedReader.readLine()).isEmpty()) {
                msg += line + "\n\r";
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return msg;
    }

    private BufferedReader getBufferedReader(InputStream in) {
        return new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
    }
}
