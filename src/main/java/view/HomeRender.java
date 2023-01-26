package view;

import model.Board;

import java.util.List;

public class HomeRender {
    private static HomeRender homeRender;

    private HomeRender() {}

    public static HomeRender getInstance() {
        if (homeRender == null) {
            homeRender = new HomeRender();
        }
        return homeRender;
    }


    public byte[] addSignInAndUpTag(byte[] homeData) {
        String homeStr = new String(homeData);
        homeStr = homeStr.replace("<!--                <li><a href=\"user/login.html\" role=\"button\">로그인</a></li>-->",
                "                <li><a href=\"user/login.html\" role=\"button\">로그인</a></li>");
        homeStr = homeStr.replace("<!--                <li><a href=\"user/form.html\" role=\"button\">회원가입</a></li>-->",
                "                <li><a href=\"user/form.html\" role=\"button\">회원가입</a></li>");
        return homeStr.getBytes();
    }

    public byte[] addUserName(byte[] homeData,String userName) {
        String homeStr = new String(homeData);
        homeStr = homeStr.replace("<!--                <li><a href=\"#\">이름</i></a></li>-->",
                "                <li><a href=\"#\">"+userName+"</i></a></li>");
        return homeStr.getBytes();
    }


    public byte[] addBoardList(byte[] homeData, List<Board> boardList) {
        String homeStr = new String(homeData);

        StringBuilder sb = new StringBuilder();
        for (Board board : boardList) {
            sb.append("<li>\n" +
                    "                    <div class=\"wrap\">\n" +
                    "                        <div class=\"main\">\n" +
                    "                            <strong class=\"subject\">\n" +
                    "                                <a href=\"./qna/show/"+board.getId()+"\">" + board.getTitle() + "</a>\n" +
                    "                            </strong>\n" +
                    "                            <div class=\"auth-info\">\n" +
                    "                                <i class=\"icon-add-comment\"></i>\n" +
                    "                                <span class=\"time\">" + board.getCreateTime() + "</span>\n" +
                    "                                <a href=\"./user/profile.html\" class=\"author\">"+board.getWriter()+"</a>\n" +
                    "                            </div>\n" +
                    "                            <div class=\"reply\" title=\"댓글\">\n" +
                    "                                <i class=\"icon-reply\"></i>\n" +
                    "                                <span class=\"point\">"+board.getId()+"</span>\n" +
                    "                            </div>\n" +
                    "                        </div>\n" +
                    "                    </div>\n" +
                    "                </li>");
        }

        homeStr = homeStr.replace("<li>\n" +
                        "                    <div class=\"wrap\">\n" +
                        "                        <div class=\"main\">\n" +
                        "                            <strong class=\"subject\">\n" +
                        "                                <a href=\"./qna/show.html\">runtime 에 reflect 발동 주체 객체가 뭔지 알 방법이 있을까요?</a>\n" +
                        "                            </strong>\n" +
                        "                            <div class=\"auth-info\">\n" +
                        "                                <i class=\"icon-add-comment\"></i>\n" +
                        "                                <span class=\"time\">2016-01-05 18:47</span>\n" +
                        "                                <a href=\"./user/profile.html\" class=\"author\">김문수</a>\n" +
                        "                            </div>\n" +
                        "                            <div class=\"reply\" title=\"댓글\">\n" +
                        "                                <i class=\"icon-reply\"></i>\n" +
                        "                                <span class=\"point\">12</span>\n" +
                        "                            </div>\n" +
                        "                        </div>\n" +
                        "                    </div>\n" +
                        "                </li>", sb.toString());

        return homeStr.getBytes();

    }
}
