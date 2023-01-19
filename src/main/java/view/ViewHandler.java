package view;

import webserver.HttpRequest;
import webserver.HttpResponse;

import java.util.Map;

public class ViewHandler {
    private static Map<String, View> views ;

    static {
        views = Map.of(
                "/user/list.html", new UserListView()
        );
    }

    public static void handle(String path, HttpRequest request, HttpResponse response, Model model) {
        if (views.containsKey(path)) {
            views.get(path).render(request, response, model);
        }
    }

}
