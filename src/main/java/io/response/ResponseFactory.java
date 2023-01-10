package io.response;

import java.io.DataOutputStream;
import java.io.OutputStream;

public class ResponseFactory {

    public HttpResponse create(OutputStream dos) {
        return new HttpResponse(new DataOutputStream(dos));
    }
}
