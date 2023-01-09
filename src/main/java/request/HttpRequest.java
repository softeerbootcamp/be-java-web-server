package request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class HttpRequest {
    private final List<String> headerContents;


    public HttpRequest(List<String> headerContents) {
        this.headerContents = headerContents;
    }

    public static HttpRequest getHttpRequest(InputStream inputStream) throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        List<String> requestHeaders = new ArrayList<>();
        while (bufferedReader.ready()) {
            requestHeaders.add(bufferedReader.readLine());
        }
        return new HttpRequest(requestHeaders);
    }

    public List<String> getHeaderContents() {
        return headerContents;
    }
}
