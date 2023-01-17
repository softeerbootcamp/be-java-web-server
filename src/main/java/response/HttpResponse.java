package response;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class HttpResponse {
    private final DataOutputStream outputStream;

    private HttpResponse(OutputStream outputStream) {
        this.outputStream = new DataOutputStream(outputStream);
    }

    public static void handleHttpResponse(OutputStream outputStream, Response response) throws IOException {
        HttpResponse httpResponse = new HttpResponse(outputStream);
        httpResponse.outputStream.writeBytes(response.getHeader());
        httpResponse.outputStream.write(response.getBody(), 0, response.getBody().length);
        httpResponse.outputStream.flush();
    }
}
