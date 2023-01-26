package webserver.controller;

import db.CommentDAO;
import exception.SessionExpiredException;
import exception.SessionNotFoundException;
import model.request.Request;
import model.response.CommentResponse;
import model.response.Response;
import util.AuthInterceptor;
import util.ViewResolver;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import static model.response.HttpStatusCode.OK;

public class DynamicHtmlController {

    public Response service(Request request) throws IOException {
        Path filePath = ViewResolver.findFilePath(request.getUrl());
        BufferedReader br = new BufferedReader(new FileReader(filePath.toFile()));
        StringBuilder body = new StringBuilder();

        if (AuthInterceptor.isAuthUser(request)) {
            return loginUserIndex(request, filePath, br, body);
        }

        return GuestIndex(request, filePath, br, body);
    }

    private Response GuestIndex(Request request, Path filePath, BufferedReader br, StringBuilder body) throws IOException {
        String line;
        while ((line = br.readLine()) != null) {
            if (line.contains("로그아웃")) {
                continue;
            }
            body.append(line);
        }

        String fileData = writeCommentLists(body);
        return Response.of(request.getHttpVersion(), OK, Map.of("Content-Type", Files.probeContentType(filePath)), fileData.getBytes());
    }

    private Response loginUserIndex(Request request, Path filePath, BufferedReader br, StringBuilder body) throws IOException {
        String line;
        while ((line = br.readLine()) != null) {
            if (line.contains("로그인") || line.contains("회원가입")) {
                continue;
            }
            if (line.contains("class=\"dropdown-toggle\"")) {
                try {
                    String name = AuthInterceptor.findUserSession(request).getName();
                    body.append("<a>").append(name).append("님</a></li><li>");

                } catch (SessionExpiredException | SessionNotFoundException e) {
                    body.append(line);
                }
            }
            body.append(line);
        }

        String fileData = writeCommentLists(body);
        return Response.of(request.getHttpVersion(), OK, Map.of("Content-Type", Files.probeContentType(filePath)), fileData.getBytes());
    }

    private String writeCommentLists(StringBuilder body) {
        CommentDAO commentDAO = new CommentDAO();

        try {
            List<CommentResponse> commentResponseList = commentDAO.findAll();
            StringBuilder sb = new StringBuilder();
            sb.append("<tr>");
            for (CommentResponse commentResponse : commentResponseList) {
                sb.append("<tr> <th scope=\"row\"><span class=\"time\">").append(commentResponse.getCreatedDate()).append("</span></th> <td class=\"auth-info\"> ").append("<a href= \"./user/profile.html\" class=\"author\">").append(commentResponse.getAuthor()).append("</a> </td> <td class=\"body\"> <span>").append(commentResponse.getContent()).append("</span> </td> </tr>");
            }
            return body.toString().replace("%commentsList%", URLDecoder.decode(sb.toString(), StandardCharsets.UTF_8));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
