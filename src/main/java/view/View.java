package view;

import Request.HttpRequest;

public interface View {
    byte[] render(HttpRequest httpRequest);
}
