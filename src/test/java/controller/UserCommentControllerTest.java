package controller;

import db.CommentDAO;
import db.SessionStorage;
import db.UserDAO;
import model.User;
import model.request.Request;
import model.response.HttpStatusCode;
import model.response.Response;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.controller.UserCommentController;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.SQLException;

public class UserCommentControllerTest {

    UserDAO userDAO = new UserDAO();
    CommentDAO commentDAO = new CommentDAO();

    @BeforeEach
    void setUp() throws SQLException {
        userDAO.deleteAll();
        commentDAO.deleteAll();
    }

    @Test
    @DisplayName("한 줄 메모 작성")
    void commentSave() throws Exception{
        //given
        User user = new User("test", "123", "tester", "test@test");
        userDAO.insert(user);
        String sid = "123";
        SessionStorage.addSession(sid, user);

        //when
        String content = "이것은 내용입니다. 하하";
        String body = "author=" + user.getName() + "&contents=" + content;
        String requestMessage = "POST /user/comment HTTP/1.1\n"
                + "Host: localhost:8080\n"
                + "Connection: keep-alive\n"
                + "Content-Length: " + body.length() + "\n"
                + "Content-Type: application/x-www-form-urlencoded\n"
                + "Cookie: sid=" + sid + "\n"
                + "Accept: */*\n\n"
                + body;
        InputStream inputStream = new ByteArrayInputStream(requestMessage.getBytes());
        Request request = new Request(inputStream);

        UserCommentController userCommentController = new UserCommentController();
        Response response = userCommentController.service(request);

        //then
        SoftAssertions.assertSoftly(softAssertions -> {
                    softAssertions.assertThat(response.getStatusLine().getHttpStatusCode()).isEqualTo(HttpStatusCode.FOUND);
                    softAssertions.assertThat(response.getHeaders().get("Location")).isEqualTo("/index.html");
                    try {
                        softAssertions.assertThat(commentDAO.findAll().size()).isEqualTo(1);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
        );
    }
}
