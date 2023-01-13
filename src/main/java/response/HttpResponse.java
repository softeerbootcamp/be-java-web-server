package response;

import request.Request;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class HttpResponse {
    private final DataOutputStream outputStream;

    private HttpResponse(OutputStream outputStream) {
        this.outputStream = new DataOutputStream(outputStream);
    }

    public static void Http200Response(Request request, OutputStream outputStream, HttpResponseBody responseBody) throws IOException {
        HttpResponse response = new HttpResponse(outputStream);
        response.outputStream.writeBytes(HttpResponseHeader.DEFAULT_HTTP_VERSION
                + " " + HttpResponseStatus.OK.getMessage());
        response.outputStream.writeBytes("Content-Type: " + request.getResourceFileContentType() + ";charset=utf-8\r\n");
        response.outputStream.writeBytes("Content-Length: " + responseBody.length() + "\r\n");
        response.outputStream.writeBytes("\r\n");

        response.outputStream.write(responseBody.getBody(), 0, responseBody.length());
        response.outputStream.flush();
    }

    public static void Http404Response(OutputStream outputStream) throws IOException {
        HttpResponse response = new HttpResponse(outputStream);
        response.outputStream.writeBytes(HttpResponseHeader.DEFAULT_HTTP_VERSION
                + " " + HttpResponseStatus.NOT_FOUND.getMessage());
        response.outputStream.writeBytes("\r\n");
        response.outputStream.flush();
    }

    public static void Http302Response(OutputStream outputStream) {
        HttpResponse response = new HttpResponse(outputStream);

    }
}
