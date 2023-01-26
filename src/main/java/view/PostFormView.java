package view;

import http.common.ContentType;
import http.request.HttpRequest;
import http.response.HttpResponse;
import util.ResourceUtils;

public class PostFormView implements View {

    @Override
    public void render(HttpRequest request, HttpResponse response, Model data) {
        byte[] bytes = ResourceUtils.loadFileFromClasspath("/qna/form.html");
        response.setBody(bytes);
        response.addHeader("Content-Type", ContentType.TEXT_HTML.getType());
    }
}
