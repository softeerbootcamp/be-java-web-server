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
}
