package webserver;

import db.Database;
import exception.UserNotFoundException;
import model.User;
import model.request.Request;
import model.response.Response;

import java.util.Map;

import static model.response.HttpStatusCode.FOUND;

public class UserService {

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
        if (isValid) {
            response.setStatusCode(request.getHttpVersion(), FOUND);
            response.addHeader("Set-Cookie", "sid=123456; Path=/");
        }
    }

}
