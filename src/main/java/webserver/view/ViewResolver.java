package webserver.view;

import java.util.Map;

public class ViewResolver {

    public static final Map<String, View> viewMap = Map.of("/user/list.html", UserListView.getInstance(),
                                                           "/index.html", IndexView.getInstance(),
                                                           "/user/profile.html", ProfileView.getInstance(),
                                                           "/board/show.html", ShowBoardView.getInstance());

    public static View getHandler(ModelAndView mv) {
        String path = mv.getViewPath();
        View targetView = viewMap.get(path);
        if(targetView != null)
            return targetView;
        return CommonView.getInstance();

    }

}
