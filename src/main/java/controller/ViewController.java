package controller;

import dto.Comment;
import dto.HttpRequest;
import dto.HttpResponse;
import dto.Session;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.CommentService;
import service.SessionService;
import service.TemplateRenderer;
import service.UserService;
import utility.HttpMethodType;
import utility.HttpStatusCode;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewController {
    // 동적 html을 반환해야 하는 html의 경우
    public static final String DEFAULT_PATH = "./src/main/resources/templates";
    private static final Logger logger = LoggerFactory.getLogger(ViewController.class);

    @ControllerMapping(method= HttpMethodType.GET, path="/index.html")
    public HttpResponse index(HttpRequest request) throws IOException {
        String path = DEFAULT_PATH + request.getPath();
        String contentType = Files.probeContentType(Path.of(path));
        TemplateRenderer renderer = new TemplateRenderer(path);;
        String sid = SessionService.extractSidFromCookie(request);

        // 한 줄 코멘트
        try {
            List<Comment> comments = new ArrayList<>(CommentService.getAll());
            StringBuilder sb = new StringBuilder();

            for(int i=1;i<=comments.size();++i) {
                Comment comment = comments.get(i-1);
                String item = String.format(
                        "                        <th scope=\"row\"><span class=\"time\">%s</span></th>\n" +
                        "                        <td class=\"auth-info\">\n" +
                        "\n" +
                        "                            <a href=\"./user/profile.html\" class=\"author\">%s</a>\n" +
                        "                        </td>\n" +
                        "                        <td class=\"body\">\n" +
                        "                            <span>%s</span>\n" +
                        "                        </td>\n", comment.getCreateDate(), comment.getUserName(), comment.getBody());

                sb.append("<tr>");
                sb.append(item);
                sb.append("        </tr>");
            }
            renderer.replaceStringWithGivenString("<!-- %comment% -->", sb.toString());
            path = "";

        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage());
        } catch (IOException e) {
            logger.error(e.getMessage());
            // TODO: InternalError / 404
        }

        // 로그인 버튼에 유저 이름 표기
        if(sid != null && !sid.isBlank()) {
            User user = null;

            try {
                Session s = SessionService.findSessionBySID(sid);
                user = UserService.findUserById(s.getUserId());
                renderer.replaceStringWithGivenString("로그인", String.format("<li><a role=\"button\">%s</a></li>", user.getUsername()));
                path = "";

                logger.debug("/index.html, userFound: {}", user.toString());
            } catch (IllegalArgumentException e) {
                logger.error(e.getMessage());
            }
        }

        return new HttpResponse(path, HttpStatusCode.OK, contentType, Map.of(), renderer.toString());
    }

    @ControllerMapping(method = HttpMethodType.GET, path = "/user/list.html")
    public HttpResponse userList(HttpRequest request) throws IOException {
        Map<String, String> header = new HashMap<>();
        HttpStatusCode status = HttpStatusCode.INTERNAL_ERROR;
        TemplateRenderer renderer = null;

        logger.debug("user - userList");

        String path = DEFAULT_PATH + request.getPath();
        String contentType = Files.probeContentType(Path.of(path));

        String sid = SessionService.extractSidFromCookie(request);

        List<User> users = null;
        if(sid != null && !sid.isBlank()) {
            try {
                renderer = new TemplateRenderer(path);

                Session s = SessionService.findSessionBySID(sid);
                // TODO: 세션에 해당하는 유저 정보 존재하는지 확인

                users = UserService.getAllUsers();
                StringBuilder sb = new StringBuilder();

                for(int i=1;i<=users.size();++i) {
                    User user = users.get(i-1);
                    String listItem =
                            String.format("<th scope=\"row\">%d</th> <td>%s</td> <td>%s</td> <td>%s</td><td><a href=\"#\" class=\"btn btn-success\" role=\"button\">수정</a></td>",
                                    i, user.getUserId(), user.getUsername(), user.getEmail());

                    sb.append("<tr>");
                    sb.append(listItem);
                    sb.append("        </tr>");
                }

                renderer.replaceStringWithGivenString("<!-- %forEach% -->", sb.toString());
                path = "";
                status = HttpStatusCode.OK;
            } catch (IllegalArgumentException e) {
                logger.error(e.getMessage());
            }
        }

        return new HttpResponse(path, status, contentType, Map.of(), renderer.toString());
    }
}
