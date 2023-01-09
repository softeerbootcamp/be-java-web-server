package request;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class ResponseGeneratorImpl implements ResponseGenerator {
    @Override
    public byte[] generate(InputStream in) {
        String method = RequestParser.getMethod(in);
        String resource = RequestParser.getResource(in);
        byte[] body = null;
        try {
            body = makeByte(method, resource);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return body;
    }

    private byte[] makeByte(String method, String resource) throws IOException {
        byte[] body = null;
        if(method.equals("GET")) {
            body = Files.readAllBytes(new File("src/resources/"
                    + (resource.contains(".html") ? "template" : "static") + resource).toPath());
        }

        return body;
    }
}
