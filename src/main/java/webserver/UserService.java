package webserver;

import db.Database;
import exception.UserNotFoundException;
import model.User;
import model.request.Request;
import model.response.Response;

import java.util.Map;

import static model.response.HttpStatusCode.FOUND;

public class UserService {

    //TODO 서비스 레벨까지 Request와 Response 객체를 계속 넘겨주지 않고 User만 넘겨주거나 할 수 없을까
    public void signUpUser(Request request) {
        Map<String, String> requestParams = request.getRequestParams();
        User user = new User(requestParams.get("userId"),
                requestParams.get("password"),
                requestParams.get("name"),
                requestParams.get("email"));
        Database.addUser(user);
    }

    public void loginUser(Request request, Response response) throws UserNotFoundException {
        User byUser = Database.findUserById(request.getRequestParams().get("userId"))
                .orElseThrow(UserNotFoundException::new);

        boolean isValid = byUser.getPassword().equals(request.getRequestParams().get("password"));
        response.setStatusCode(request.getHttpVersion(), FOUND);
        if (isValid) {
            response.addHeader("Set-Cookie", "sid=123456; Path=/");
            response.addHeader("Location", "/index.html");
            return;
        }
        response.addHeader("Location", "/user/login_failed.html");
        throw new UserNotFoundException();
    }

}
