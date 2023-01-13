package request;

import javax.sound.sampled.Line;
import java.io.BufferedReader;
import java.io.IOException;

public class Request {


    private RequestLine requestLine;
    //public String getRequestLine;

    public Request(BufferedReader bufferedReader) throws IOException {
        setRequestLine(bufferedReader.readLine());
    }

    public void setRequestLine(String line) {
        this.requestLine = new RequestLine(line);
    }

    public RequestLine getRequestLine() {
        return this.requestLine;
    }
}
