package webserver.view;

import model.dto.BoardDto;
import model.dto.CommentDto;
import model.dto.ReadBoardDto;
import webserver.domain.StatusCodes;
import webserver.domain.request.Request;
import webserver.domain.response.Response;
import webserver.utils.StaticResourceFinder;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ShowBoardView implements View{

    private ShowBoardView(){}
    private static StringBuilder sb = new StringBuilder();
    public static ShowBoardView getInstance(){
        return ShowBoardView.LazyHolder.INSTANCE;
    }

    private static class LazyHolder{
        private static final ShowBoardView INSTANCE = new ShowBoardView();
    }

    private String getBoardScript(BoardDto boardDto) {

        sb.setLength(0);
        sb.append("<h2 class=\"qna-title\">%s</h2>\n </header>\n <div class=\"content-main\">\n")
            .append("<article class=\"article\">\n <div class=\"article-header\">\n <div class=\"article-header-thumb\">\n")
            .append("<img src=\"https://graph.facebook.com/v2.3/100000059371774/picture\" class=\"article-author-thumb\" alt=\"\">\n </div>\n")
            .append("<div class=\"article-header-text\">")
            .append("<a href=\"/user/profile/%s\" class=\"article-author-name\">%s</a>\n")
            .append("<a href=\"/questions/413\" class=\"article-header-time\" title=\"퍼머링크\">\n")
            .append("%s\n")
            .append("<i class=\"icon-link\"></i>\n </a>\n</div>\n</div>\n<div class=\"article-doc\">\n<p>%s</p>");

        return String.format(sb.toString(), boardDto.getTitle(), boardDto.getWriterId(), boardDto.getWriterName(), boardDto.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), boardDto.getContents());
    }

    private String getCommentScript(List<CommentDto> commentDto){

        sb.setLength(0);
        sb.append("<article class=\"article\" id=\"answer-1406\">\n <div class=\"article-header\">\n")
            .append("<div class=\"article-header-thumb\">\n")
            .append("<img src=\"https://graph.facebook.com/v2.3/1324855987/picture\" class=\"article-author-thumb\" alt=\"\">\n </div>\n")
            .append("<div class=\"article-header-text\">\n")
            .append("<a href=\"/user/profile/%s\" class=\"article-author-name\">%s</a>\n")
            .append("<a href=\"#answer-1434\" class=\"article-header-time\" title=\"퍼머링크\">\n")
            .append("%s\n </a>\n </div>\n </div>\n <div class=\"article-doc comment-doc\">\n")
            .append("<p>%s</p>\n")
            .append("</div>\n <div class=\"article-utils\">\n <ul class=\"article-utils-list\">\n <li>\n")
            .append("<a class=\"link-modify-article\" href=\"/questions/413/answers/1405/form\">수정</a>\n")
            .append("</li>\n <li>\n <form class=\"delete-answer-form\" action=\"/questions/413/answers/1405\" method=\"POST\">\n")
            .append("<input type=\"hidden\" name=\"_method\" value=\"DELETE\">\n")
            .append("<button type=\"submit\" class=\"delete-answer-button\">삭제</button>\n </form>\n </li>\n </ul>\n </div>\n </article>");

        String rawStr = sb.toString();

        commentDto.forEach(comment -> sb.append(String.format(rawStr, comment.getWriterId(), comment.getWriterName(), comment.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), comment.getContents())));
        return sb.toString();
    }
    private String getWriteCommentScript(BoardDto boardDto){
        String rawStr = "<form class=\"answer-form\" method=\"post\" id=\"answer\" action=\"/board/%d/createComment\">";
        return String.format(rawStr, boardDto.getBoardId());
    }
    @Override
    public void render(Request req, Response res, ModelAndView mv, String menuBar, String nameTag) throws IOException {
        ReadBoardDto readBoardDto = ReadBoardDto.class.cast(mv.getViewModel().get("board"));
        StaticResourceFinder.staticFileResolver(mv.getViewPath())
                .ifPresent( fileAsBytes -> {
                    String fileContent = new String(fileAsBytes);
                    fileContent = fileContent.replace(menuBar, "")
                            .replace("<li id=\"username\"></li>", nameTag)
                            .replaceAll("(?is)<!-- post start -->.+?<!-- post end -->", getBoardScript(readBoardDto.getBoardDto()))
                            .replaceAll("(?is)<!-- comment start -->.+?<!-- comment end -->", getCommentScript(readBoardDto.getCommentDtos()))
                            .replaceAll("(?is)<!-- write comment start -->.+?<!-- write comment end -->", getWriteCommentScript(readBoardDto.getBoardDto()));
                    res.addHeaderAndBody(StatusCodes.OK, fileContent.getBytes(), StaticResourceFinder.getExtension(mv.getViewPath()));
                });
    }
}
