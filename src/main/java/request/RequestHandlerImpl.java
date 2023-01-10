package request;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ResponseGeneratorImpl implements ResponseGenerator {
    @Override
    public void generate(InputStream in, DataOutputStream dos, int port) {
        Request request = Request.of(in);
        System.out.println(port + ": " + request);
        byte[] body = new byte[0];
        try {
            for (HttpMethod method : HttpMethod.values()) {
                if (request.getMethod().equals(method.getMethod())) {
                    body = method.handle(request);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
