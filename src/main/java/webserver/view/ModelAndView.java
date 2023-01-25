package webserver.view;

import java.util.HashMap;
import java.util.Map;

public class ModelAndView {

    public String viewPath;  //url of the resource
    public Map<String, Object> viewModel = new HashMap<>();  //data to be programmatically bound to static files

    public String getViewPath() {
        return viewPath;
    }

    public void setViewPath(String viewPath) {
        this.viewPath = viewPath;
    }

    public Map<String, Object> getViewModel() {
        return viewModel;
    }

    public void addViewModel(String key, Object value) {
        viewModel.put(key, value);
    }
}
