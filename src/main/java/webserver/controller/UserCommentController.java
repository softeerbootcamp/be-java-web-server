package webserver.controller;

import db.CommentDAO;
import model.request.Request;
import model.response.Response;

public class UserCommentController implements UserController {

    private final CommentDAO commentDAO = new CommentDAO();

    @Override
    public Response service(Request request) {

//        commentDAO.insert(new Comment(author, content));
        return null;
    }
}
