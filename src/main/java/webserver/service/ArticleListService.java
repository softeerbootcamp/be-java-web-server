package webserver.service;

import db.mysql.DB_Board;
import db.mysql.DB_Users;
import model.Article;
import model.User;
import webserver.constants.InBody;
import webserver.httpUtils.Request;
import webserver.httpUtils.Response;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;

public class ArticleListService implements Service{
    public ArticleListService(){}
    @Override
    public Response exec(Request req) throws IOException {
        String reqQuery = req.getReqLine().getQuery();
        byte[] bodyByte = Service.urlToByte(reqQuery);
        String contentType = Files.probeContentType(new File(reqQuery).toPath());

        String bodyStr = new String(bodyByte);
        bodyStr = bodyStr.replace(InBody.beforeReplaced_ArticleList, generateAllArticleList());
        bodyByte = bodyStr.getBytes();

        return new Response()
                .withVersion(req.getReqLine().getVersion())
                .withStatCodeAndText(200, "OK")
                .withHeaderKeyVal("Content-Type", contentType + ";charset=utf-8")
                .withHeaderKeyVal("Content-Length", Integer.toString(bodyByte.length))
                .withBodyBytes(bodyByte);
    }

    String generateAllArticleList()
    {
        StringBuffer sb = new StringBuffer();
        try{
            for(Article article : DB_Board.findAll())
            {
                sb
                        .append("<li>")
                            .append("<div class=\"wrap\">")
                                .append("<div class=\"main\">")
                                    .append("<strong class=\"subject\">\n<a href=\"./qna/show.html\">"+article.getContent()+"</a>\n</strong>")
                                    .append("<div class=\"auth-info\">")
                                        .append("<span class=\"time\">"+article.getDate()+"     </span>")
                                        .append("<a href=\"./user/profile.html\" class=\"author\">"+"작성자 ID : "+ article.getWriterId()+"</a>")
                                    .append("</div>")
                                .append("</div>")
                            .append("</div>")
                        .append("</li>");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        return sb.toString();
    }
}
