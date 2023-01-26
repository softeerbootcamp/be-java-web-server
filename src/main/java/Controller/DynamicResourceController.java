package Controller;

import http.request.HttpRequest;
import http.response.HttpResponse;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.MemoService;
import service.UserService;
import view.HomeView;
import view.LoginView;
import webserver.session.Session;
import webserver.session.SessionConst;

import java.io.IOException;

public class DynamicResourceController implements Controller {

    private static final Logger logger = LoggerFactory.getLogger(DynamicResourceController.class);
    private static final DynamicResourceController instance = new DynamicResourceController();

    public static DynamicResourceController getInstance() {
        return instance;
    }

    private DynamicResourceController() {
    }

    @Override
    public void process(HttpRequest request, HttpResponse response) {

        String url = request.getUrl();
        if (url.equals("/")) {
            url = "/index.html";
        }

        if (!request.isLogin() && !url.equals("/index.html")) {
            StaticResourceController.getInstance().process(request, response);
            return;
        }

        try {
            String body = "";
            if (url.equals("/index.html")) {
                body = HomeView.render(url, MemoService.getInstance().findMemoList(), body);
            }

            if (request.isLogin()) {
                Session session = request.getSession();
                String userId = session.getAttribute(SessionConst.USER_ID);

                User loginUser = UserService.getInstance().findUserById(userId);

                body = LoginView.render(loginUser.getName(), url, body);
            }

            response.ok(request);
            response.setBodyMessage(body);
        } catch (IOException e) {
            logger.error(e.getMessage());
            response.notFound(request);
        }
    }
}
