package view;

import http.request.HttpRequest;
import http.response.HttpResponse;
import java.util.Map;

public class ViewHandler {
    private static final Map<String, View> views;

    static {
        views = Map.of(
                "/index.html", new IndexView(),
                "/user/list.html", new UserListView(),
                "/user/login.html", new LoginView(),
                "/user/form.html", new SignUpView(),
                "/qna/form.html", new PostFormView()
        );
    }

    public static void handle(String path, HttpRequest request, HttpResponse response, Model model) {
        if (views.containsKey(path)) {
            views.get(path).render(request, response, model);
        }
    }
}
