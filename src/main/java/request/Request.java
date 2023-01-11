package request;

import java.io.InputStream;
import java.util.Map;

public class Request {
    private final Map<String, String> requestHeader;

    private Request(InputStream in) {
        requestHeader = RequestParser.parse(in);
    }

    public static Request of(InputStream in) {
        return new Request(in);
    }

    public String getMethod() {
        String[] tokens = requestHeader.get(RequestParser.REQUEST_LINE).split(" ");
        return tokens[0];
    }

    public String getResource() {
        String[] tokens = requestHeader.get(RequestParser.REQUEST_LINE).split(" ");
        return tokens[1];
    }

    @Override
    public String toString() {
        return requestHeader.toString();
    }
}
