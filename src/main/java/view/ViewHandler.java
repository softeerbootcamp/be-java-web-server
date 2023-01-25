package view;

import request.HttpRequest;
import response.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

public class ViewHandler {
    private static Map<String, View> views ;

    static {
        views = Map.of(
                "./templates/index.html", new IndexView(),
                "./templates/user/list.html", new UserListView(),
                "./templates/user/login.html", new LoginView(),
                "./templates/user/login_failed.html", new LoginFailView(),
                "./templates/user/form.html", new SignUpView(),
                "./templates/qna/form.html", new FormView(),
                "./templates/qna/show.html", new QnaDetailView()
        );
    }

    public static void handle(String path, HttpRequest request, HttpResponse response, Model model) {
        try{
            if (views.containsKey(path)) {
                views.get(path).render(request, response, model);
            }
        }catch(IOException | URISyntaxException e){
            e.printStackTrace();
        }

    }

}
