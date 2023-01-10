package http;

import org.checkerframework.checker.units.qual.A;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HttpRequest {

    private final RequestLine requestLine;
    private final RequestHeader requestHeader;

    public HttpRequest(
            RequestLine requestLine,
            RequestHeader requestHeader
    ) {
        this.requestLine = requestLine;
        this.requestHeader = requestHeader;
    }

    public static HttpRequest from(BufferedReader br) throws IOException {
        String line = br.readLine();
        List<String> requests = new ArrayList<>();
        while(!line.equals("")) {
            requests.add(line);
            line = br.readLine();
        }
        return new HttpRequest(
                RequestLine.from(requests.get(0)),
                RequestHeader.from(requests.subList(1, requests.size()))
        );
    }
}
