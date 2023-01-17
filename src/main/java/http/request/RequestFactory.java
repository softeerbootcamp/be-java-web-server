package http.request;

import http.common.Body;
import http.common.Header;
import http.common.HeaderAttribute;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class RequestFactory {

    public HttpRequest create(InputStream in) throws IOException {
        BufferedReader bufferedReader = getBufferedReader(in);

        RequestStartLine startLine = new RequestStartLine(bufferedReader.readLine());
        Header header = new Header(getRawHeaderMessage(bufferedReader));
        Integer contentLength = Integer.parseInt(header.getAttribute(HeaderAttribute.CONTENT_LENGTH));
        Body body = new Body(getRawBodyMessage(contentLength, bufferedReader));

        return new HttpRequest(startLine, header, body);
    }

    private String getRawBodyMessage(Integer contentLength, BufferedReader bufferedReader) throws IOException {
        String msg = "";
        for (int i = 0; i < contentLength; i++) {
            msg += (char) bufferedReader.read();
        }
        return msg;
    }

    private String getRawHeaderMessage(BufferedReader bufferedReader) throws IOException {
        String msg = "";
        String line;
        while (!(line = bufferedReader.readLine()).isEmpty()) {
            msg += line + "\n";
        }
        return msg;
    }

    private BufferedReader getBufferedReader(InputStream in) {
        return new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
    }
}
