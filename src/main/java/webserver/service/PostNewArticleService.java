package webserver.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.constants.Paths;
import webserver.controller.DynamicFileController;
import webserver.httpUtils.Request;
import webserver.httpUtils.Response;
import db.mysql.DB_Board;

import java.io.IOException;
import java.net.URLDecoder;
import java.sql.SQLException;

public class PostNewArticleService extends AlreadyLoggedInService{
    private static final Logger logger = LoggerFactory.getLogger(PostNewArticleService.class);

    public PostNewArticleService(String sid_userid){
        super(sid_userid);
    }

    public Response exec(Request req) throws IOException {
        String body = URLDecoder.decode(new String(req.getReqBody().getContext()));
        String content = splitContent(body);

        postNewArticle(content);

        return new Response()
                .withVersion(req.getReqLine().getVersion())
                .withStatCodeAndText(302, "REDIR")
                .withHeaderKeyVal("Location", Paths.HOME_PATH);
    }

    private String splitContent(String before)
    {
        String onlyUseIdx1[] = before.split("=");
        return onlyUseIdx1[1];
    }

    private void postNewArticle(String article)
    {
        try {
            logger.debug("trying add new article");
            DB_Board.addArticle(article, super.getSid_usrid());
        }catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}
