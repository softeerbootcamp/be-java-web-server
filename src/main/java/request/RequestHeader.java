package request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RequestHeader {
    private final HashMap<String, String> headerContents;
    public static final String REQUEST_LINE = "Request Line";

    public RequestHeader(HashMap<String, String> headerContents) {
        this.headerContents = headerContents;
    }

    protected static RequestHeader makeRequestHeader(BufferedReader bufferedReader) throws IOException {
        HashMap<String, String> headerContents = new HashMap<>();

        //RequestLine 먼저 읽기
        headerContents.put(REQUEST_LINE, bufferedReader.readLine());

        String headerLine;
        while ((headerLine = bufferedReader.readLine()) != null && !headerLine.isEmpty()) {
            Matcher matcher = Pattern.compile("(?<name>.+?):(?<value>.+)").matcher(headerLine);
            if (matcher.matches()) {
                String headerName = matcher.group("name").trim();
                String headerValue = matcher.group("value").trim();
                headerContents.put(headerName, headerValue);
            }
        }

        return new RequestHeader(headerContents);
    }


    public HashMap<String, String> getHeaderContents() {
        return headerContents;
    }
}
