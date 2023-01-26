package webserver.controller;

import db.CommentDAO;
import model.Comment;
import model.UserSession;
import model.request.Request;
import model.response.Response;
import util.AuthInterceptor;

import java.sql.SQLException;
import java.util.Map;

import static model.response.HttpStatusCode.FOUND;

public class UserCommentController implements UserController {

    private final CommentDAO commentDAO = new CommentDAO();

    @Override
    public Response service(Request request) {

        if (AuthInterceptor.isAuthUser(request)) {
            try {
                UserSession userSession = AuthInterceptor.findUserSession(request);
                commentDAO.insert(new Comment(userSession.getUserId(), userSession.getName(), request.getRequestParams().get("contents")));
                return Response.of(request.getHttpVersion(), FOUND, Map.of("Location", "/index.html"), new byte[0]);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return Response.of(request.getHttpVersion(), FOUND, Map.of("Location", "/user/login.html"), new byte[0]);
    }
}
