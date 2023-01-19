package view;

import webserver.HttpRequest;
import webserver.HttpResponse;

import java.util.Map;

public class ViewHandler {
    private static Map<String, View> views ;

    static {
        views = Map.of(
                "./templates/index.html", new IndexView(),
                "/user/list.html", new UserListView(),
                "./templates/user/login.html", new LoginView(),
                "./templates/user/login_failed.html", new LoginFailView(),
                "./templates/user/form.html", new SignUpView()
        );
    }

    public static void handle(String path, HttpRequest request, HttpResponse response, Model model) {
        if (views.containsKey(path)) {
            views.get(path).render(request, response, model);
        }
    }

}
