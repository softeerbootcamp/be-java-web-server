package webserver.service;

import db.SessionStorage;
import db.UserDAO;
import exception.UserNotFoundException;
import model.User;
import model.UserSession;
import model.request.Request;
import model.request.UserCreate;
import model.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;

import java.sql.SQLException;
import java.util.Map;
import java.util.UUID;

import static model.response.HttpStatusCode.FOUND;

public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserDAO userDAO = new UserDAO();

    public void signUpUser(Request request) {
        Map<String, String> requestParams = request.getRequestParams();
        UserCreate userCreate = new UserCreate(requestParams.get("userId"),
                requestParams.get("password"),
                requestParams.get("name"),
                requestParams.get("email"));
        try {
            userDAO.insert(userCreate);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Response loginUser(Request request) {
        try {
            User user = validateAccount(request);
            String sid = String.valueOf(UUID.randomUUID());
            SessionStorage.addSession(sid, user);
            logger.debug("로그인 성공 (세션 저장) user id : {}, sid : {}", user.getUserId(), sid);

            return Response.of(request.getHttpVersion(), FOUND, Map.of("Set-Cookie", "sid=" + sid + "; Path=/",
                    "Location", "/index.html"), new byte[0]);
        } catch (SQLException | UserNotFoundException e) {
            logger.debug("로그인 실패!!");
            return Response.of(request.getHttpVersion(), FOUND, Map.of("Location", "/user/login_failed.html"), new byte[0]);
        }
    }

    private User validateAccount(Request request) throws SQLException {

        User byUser = userDAO.findByUserId(request.getRequestParams().get("userId"));
        boolean isValid = byUser.getPassword().equals(request.getRequestParams().get("password"));
        if (isValid) {
            return byUser;
        }
        throw new UserNotFoundException();
    }

    public Response logoutUser(Request request) {
        String sid = HttpRequestUtils.parseSid(request.getHeaders().get("Cookie"));
        UserSession bySessionId = SessionStorage.findBySessionId(sid);
        bySessionId.expire();
        return Response.of(request.getHttpVersion(), FOUND, Map.of("Location", "/index.html"), new byte[0]);
    }

}
