package Controller;

import db.Database;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.User;
import view.HomeLoginView;
import webserver.session.Session;
import webserver.session.SessionConst;

public class HomeLoginController implements Controller {

    private static final HomeLoginController instance = new HomeLoginController();

    public static HomeLoginController getInstance() {
        return instance;
    }

    private HomeLoginController() {
    }

    @Override
    public void process(HttpRequest request, HttpResponse response) {
        Session session = request.getSession();
        String userId = session.getAttribute(SessionConst.USER_ID);

        User loginUser = Database.findUserById(userId);

        String body = HomeLoginView.render(loginUser.getName());

        response.ok(request);
        response.setBodyMessage(body);
    }
}
