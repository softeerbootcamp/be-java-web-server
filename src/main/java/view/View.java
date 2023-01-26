package view;

import request.HttpRequest;

public interface View {
    byte[] render(HttpRequest httpRequest);
}
