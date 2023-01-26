package controller;

import dto.HttpRequest;
import dto.HttpResponse;
import dto.Session;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.CommentService;
import service.SessionService;
import service.UserService;
import utility.HttpMethodType;
import utility.HttpRequestUtils;
import utility.HttpStatusCode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class CommentController {
    private static final Logger logger = LoggerFactory.getLogger(CommentController.class);

    @ControllerMapping(method = HttpMethodType.POST, path = "/comment/write")
    public HttpResponse createComment(HttpRequest request) throws IOException {
        String contentType = "text/html";
        HttpStatusCode status = HttpStatusCode.NOT_FOUND;
        Map<String, String> header = new HashMap<>();
        String body = "";
        User user = null;

        if(!request.getBody().isBlank())
            body = HttpRequestUtils.parseQueryString(request.getBody()).get("contents");

        logger.debug("/comment/write body: {}", body);

        String sid = SessionService.extractSidFromCookie(request);

        if(sid != null && !sid.isBlank()) {
            try {
                Session s = SessionService.findSessionBySID(sid);
                user = UserService.findUserById(s.getUserId());

            } catch (IllegalArgumentException e) {  // 존재하는 유저 아니면 Exception
                logger.error(e.getMessage());
            }
        }

        if(user == null)
            return new HttpResponse("/index.html", HttpStatusCode.FOUND, "text/html", header);

        String path = "";
        status = HttpStatusCode.FOUND;
        header.put("Location", "/index.html");

        try {
            CommentService.createComment(body, user.getUserId(), user.getUsername());
        } catch (RuntimeException e) {
            logger.error("글쓰기 불가 {}", e.getMessage());
            header.put("Location", "/user/form.html");
        }

        if(!path.isBlank())
            contentType = Files.probeContentType(Path.of(path));

        return new HttpResponse(path, status, contentType, header);
    }
}
