package request;

import java.io.BufferedReader;
import java.io.IOException;

public class RequestBody {

    private final StringBuilder data;

    public RequestBody(StringBuilder data) {
        this.data = data;
    }

    protected static RequestBody makeRequestBody(BufferedReader bufferedReader) throws IOException {
        StringBuilder data = new StringBuilder();
        while (bufferedReader.ready()) {
            data.append((char) bufferedReader.read());
        }
        return new RequestBody(data);
    }

    public StringBuilder getData() {
        return data;
    }
}
