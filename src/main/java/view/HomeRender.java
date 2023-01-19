package view;

public class HomeRender {
    private static HomeRender homeRender;

    private HomeRender() {}

    public static HomeRender getInstance() {
        if (homeRender == null) {
            homeRender = new HomeRender();
        }
        return homeRender;
    }

    public byte[] addSignInAndUpTag(String homeData) {
        homeData = homeData.replace("<!--                <li><a href=\"user/login.html\" role=\"button\">로그인</a></li>-->",
                "                <li><a href=\"user/login.html\" role=\"button\">로그인</a></li>");
        homeData = homeData.replace("<!--                <li><a href=\"user/form.html\" role=\"button\">회원가입</a></li>-->",
                "                <li><a href=\"user/form.html\" role=\"button\">회원가입</a></li>");
        return homeData.getBytes();
    }
}
