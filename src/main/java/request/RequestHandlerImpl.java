package request;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class RequestHandlerImpl implements RequestHandler {
    @Override
    public void handleRequest(InputStream in, DataOutputStream dos, int port) {
        Request request = Request.of(in);
        System.out.println(port + ": " + request);
        try {
            for (HttpMethod method : HttpMethod.values()) {
                if (request.getMethod().equals(method.getMethod())) {
                    method.handle(request, dos);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
