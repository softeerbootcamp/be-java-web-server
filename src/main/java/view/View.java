package view;

import request.HttpRequest;
import response.HttpResponse;

public interface View {
    void render(HttpRequest request, HttpResponse response, Model data);

}
