package view;

import http.common.ContentType;
import http.request.HttpRequest;
import http.request.HttpSession;
import http.response.HttpResponse;
import util.ResourceUtils;

public class IndexView implements View {

    @Override
    public void render(HttpRequest request, HttpResponse response, Model data) {
        byte[] bytes = request.getSession() == null ? guest() : auth(request.getSession());
        response.setBody(bytes);
        response.addHeader("Content-Type", ContentType.TEXT_HTML.name());
    }

    private byte[] guest() {
        return ResourceUtils.loadFileFromClasspath("/index.html");
    }

    private byte[] auth(HttpSession session) {
        String target = "<li><a href=\"user/login.html\" role=\"button\">로그인</a></li>";
        String name = "<!--<li>name</li>-->";
        String replaceName = "<li>" + session.user().getName() + "</li>";
        String page = new String(ResourceUtils.loadFileFromClasspath("/index.html"));

        return page.replace(target, "").replace(name, replaceName).getBytes();
    }
}
