package view;

import http.common.ContentType;
import http.request.HttpRequest;
import http.request.HttpSession;
import http.response.HttpResponse;
import service.Post;
import util.ResourceUtils;

import java.util.List;

public class IndexView implements View {

    private static final String postTags = "<li>\n" +
            "                  <div class=\"wrap\">\n" +
            "                      <div class=\"main\">\n" +
            "                          <strong class=\"subject\">\n" +
            "                              <a href=\"./qna/show.html\"><!--title--></a>\n" +
            "                          </strong>\n" +
            "                          <div><!--content--></div>\n" +
            "                          <div class=\"auth-info\">\n" +
            "                              <i class=\"icon-add-comment\"></i>\n" +
            "                              <span class=\"time\"><!--time--></span>\n" +
            "                              <a href=\"./user/profile.html\" class=\"author\"><!--writer--></a>\n" +
            "                          </div>\n" +
            "                          <div class=\"reply\" title=\"댓글\">\n" +
            "                              <i class=\"icon-reply\"></i>\n" +
            "                              <span class=\"point\"><!--index--></span>\n" +
            "                          </div>\n" +
            "                      </div>\n" +
            "                  </div>\n" +
            "              </li>";

    @Override
    public void render(HttpRequest request, HttpResponse response, Model data) {
        byte[] bytes = page(request, data);
        response.setBody(bytes);
        response.addHeader("Content-Type", ContentType.TEXT_HTML.name());
    }

    private byte[] page(HttpRequest request, Model model) {
        String page = new String(ResourceUtils.loadFileFromClasspath("/index.html"));
        page = drawPosts(page, model);

        if (request.getSession() == null) {
            return page.getBytes();
        }

        return auth(page, request.getSession());
    }

    private String drawPosts(String page, Model data) {
        StringBuilder sb = new StringBuilder();
        String content = "<!--post-->";

        for (Object o: (List<?>) data.getAttribute("data")) {
            Post post = (Post) o;

            String copy = postTags;
            copy = copy.replace("<!--title-->", post.getTitle());
            copy = copy.replace("<!--writer-->", post.getWriter());
            copy = copy.replace("<!--content-->", post.getContent());
            copy = copy.replace("<!--time-->", post.getDate());
            copy = copy.replace("<!--index-->", String.valueOf(post.getId()));
            sb.append(copy).append("\n");
        }
        return page.replace(content, sb.toString());
    }

    private byte[] auth(String page, HttpSession session) {
        String target = "<li><a href=\"user/login.html\" role=\"button\">로그인</a></li>";
        String name = "<!--<li>name</li>-->";
        String replaceName = "<li>" + session.user().getName() + "</li>";

        return page.replace(target, "").replace(name, replaceName).getBytes();
    }
}
