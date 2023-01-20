package view;

import request.HttpRequest;
import response.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;

public interface View {
    void render(HttpRequest request, HttpResponse response, Model data) throws IOException, URISyntaxException;

}
