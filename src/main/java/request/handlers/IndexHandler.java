package request.handlers;

import file.FileContentType;
import model.Post;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.Request;
import request.RequestHandler;
import response.HttpResponseStatus;
import response.Response;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class IndexHandler implements RequestHandler {
    Logger logger = LoggerFactory.getLogger(IndexHandler.class);

    private static final IndexHandler instance;

    static {
        instance = new IndexHandler();
    }

    private IndexHandler() {}

    public static IndexHandler getInstance() {
        return instance;
    }

    @Override
    public Response doGet(Request request) {
        try {
            String resource = request.getResource();
            if(!resource.endsWith(".html")) {
                resource += ".html";
            }
            String filePath = "src/main/resources/templates" + resource;
            String content = generateDynamicHeader(request.getCookie(), filePath);
            content = generatePostList(content);
            return Response.createSimpleResponse(content.getBytes(), FileContentType.HTML.getContentType(), HttpResponseStatus.OK);
        } catch (IOException e) {
            return Response.from(HttpResponseStatus.NOT_FOUND);
        } catch (SQLException e) {
            logger.error(Arrays.toString(e.getStackTrace()));
            return  Response.from(HttpResponseStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private String generatePostList(String content) throws SQLException {
        List<Post> postList = postService.getAllPosts();
        StringBuilder stringBuilder = new StringBuilder();
        for(Post post : postList) {
            String elem = "<li>\n" +
                    "    <div class=\"wrap\">\n" +
                    "        <div class=\"main\">\n" +
                    "            <strong class=\"subject\">\n" +
                    "                <a href=\"./qna/show.html\">" + post.getTitle() + "</a>\n" +
                    "            </strong>\n" +
                    "            <div class=\"auth-info\">\n" +
                    "                <i class=\"icon-add-comment\"></i>\n" +
                    "                <span class=\"time\">" + post.getCreatedTime().toString() + "</span>\n" +
                    "                <a href=\"./user/profile.html\" class=\"author\">" + post.getUserId() + "</a>\n" +
                    "            </div>\n" +
                    "            <div class=\"reply\" title=\"댓글\">\n" +
                    "                <i class=\"icon-reply\"></i>\n" +
                    "                <span class=\"point\">" + post.getId() + "</span>\n" +
                    "            </div>\n" +
                    "        </div>\n" +
                    "    </div>\n" +
                    "</li>";
            stringBuilder.append(elem);
        }

        return content.replace("${postList}", stringBuilder.toString());
    }
}
