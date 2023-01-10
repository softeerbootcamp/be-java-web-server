package io.request;

import util.Descriptor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class RequestFactory {

    public Request create(InputStream in) throws IOException {
        BufferedReader bufferedReader = getBufferedReader(in);
        String wholeMessage = Descriptor.getWholeMessage(bufferedReader);
        return new RequestImpl(wholeMessage);
    }

    private BufferedReader getBufferedReader(InputStream in) {
        return new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
    }
}
