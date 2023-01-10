package request;

import java.io.IOException;
import java.io.InputStream;

public class ResponseGeneratorImpl implements ResponseGenerator {
    @Override
    public byte[] generate(InputStream in, int port) {
        Request request = Request.of(in);
        System.out.println(port + ": " + request);
        byte[] body = null;
        try {
            for (HttpMethod method : HttpMethod.values()) {
                if (request.getMethod().equals(method.getMethod())) {
                    body = method.handle(request);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return body;
    }
}
