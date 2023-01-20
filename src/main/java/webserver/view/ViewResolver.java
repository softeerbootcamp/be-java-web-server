package webserver.view;

import webserver.domain.ModelAndView;
import java.util.Map;

public class ViewResolver {

    public static final Map<String, View> viewMap = Map.of("/user/list.html", UserListView.getInstance());

    public static View getHandler(ModelAndView mv) {
        String path = mv.getViewPath();
        View targetView = viewMap.get(path);
        if(targetView != null)
            return targetView;
        return CommonView.getInstance();

    }

}
