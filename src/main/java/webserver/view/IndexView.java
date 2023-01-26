package webserver.view;

import model.Board;
import webserver.domain.StatusCodes;
import webserver.domain.request.Request;
import webserver.domain.response.Response;
import webserver.utils.StaticResourceFinder;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class IndexView implements View{

    private IndexView(){}

    public static IndexView getInstance(){
        return IndexView.LazyHolder.INSTANCE;
    }

    private static class LazyHolder{
        private static final IndexView INSTANCE = new IndexView();
    }

    private String getTableOfBoard(Object boardObjList){     //make html <tbody> tag with userList
        StringBuilder sb = new StringBuilder();
        sb.append("<li> <div class=\"wrap\"> <div class=\"main\">\n <strong class=\"subject\">");
        sb.append("<a href=\"./board/show/%d\">%s</a>\n");
        sb.append("</strong> <div class=\"auth-info\"> <i class=\"icon-add-comment\"></i>");
        sb.append("<span class=\"time\">%s</span>");
        sb.append("<a href=\"./user/profile/%s\" class=\"author\">%s</a>\n </div>\n");
        sb.append("<div class=\"reply\" title=\"댓글\">\n");
        sb.append("<i class=\"icon-reply\"></i>\n");
        sb.append("<span class=\"point\">%d</span>\n </div>\n </div>\n </div>\n </li>");

        String rawStr = sb.toString();
        sb.setLength(0);

        ArrayList<Board> boardList = ArrayList.class.cast(boardObjList);
        boardList.forEach(board-> sb.append(String.format(rawStr,board.getBoardId(), board.getTitle(), board.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),board.getWriterId(), board.getWriterName(), board.getBoardId())));
        return sb.toString();
    }
    @Override
    public void render(Request req, Response res, ModelAndView mv, String menuBar, String nameTag) throws IOException {
        String userTableScript = getTableOfBoard(mv.getViewModel().get("board"));
        StaticResourceFinder.staticFileResolver(mv.getViewPath())
                .ifPresent( fileAsBytes -> {
                    String fileContent = new String(fileAsBytes);
                    fileContent = fileContent.replace(menuBar, "")
                            .replace("<li id=\"username\"></li>", nameTag)
                            .replaceAll("(?is)<!-- container start -->.+?<!-- container end -->", userTableScript);
                    res.addHeaderAndBody(StatusCodes.OK, fileContent.getBytes(), StaticResourceFinder.getExtension(mv.getViewPath()));
                });
    }
}
