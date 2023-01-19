package view;

import webserver.HttpRequest;
import webserver.HttpResponse;

public interface View {
    void render(HttpRequest request, HttpResponse response, Model data);

}
