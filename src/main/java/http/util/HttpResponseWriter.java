package http.util;

import http.common.HttpBody;
import http.common.HttpHeaders;
import http.common.HttpStatus;
import http.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class HttpResponseWriter {
    private HttpResponseWriter() {}
    private static final Logger logger = LoggerFactory.getLogger(HttpResponseWriter.class);

    private static final String HTTP_VERSION = "HTTP/1.1";

    public static void write(OutputStream out, HttpResponse response) {
        try {
            DataOutputStream dos = new DataOutputStream(out);
            writeStatus(dos, response.getStatus());
            writeHeaders(dos, response.getHeaders());
            writeBody(dos, response.getBody());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private static void writeStatus(DataOutputStream dos, HttpStatus status) throws IOException {
        String statusLine = HTTP_VERSION + " " + status.code() + " " + status.name() + "\r\n";
        dos.writeBytes(statusLine);
    }

    private static void writeHeaders(DataOutputStream dos, HttpHeaders headers) throws IOException {
        for (String key: headers.keys()) {
            String header = key + " : " + headers.getValue(key) + "\r\n";
            dos.writeBytes(header);
        }
    }

    private static void writeBody(DataOutputStream dos, HttpBody body) throws IOException {
        dos.writeBytes("\n");
        dos.write(body.data());
        dos.flush();
    }
}
