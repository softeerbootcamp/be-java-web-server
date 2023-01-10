package request;

import java.io.BufferedReader;
import java.io.IOException;

public class Request {

    private static RequestLine requestLine;

    public Request(BufferedReader bufferedReader) throws IOException {
        setRequestLine(bufferedReader.readLine());
    }
    public static void setRequestLine(String line) {
        Request.requestLine = new RequestLine(line);
    }

}
