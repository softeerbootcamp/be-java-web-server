package webserver.domain;

import java.util.HashMap;
import java.util.Map;

public class ModelAndView {

    public String viewName;
    public Map<String, Object> viewModel = new HashMap<>();

    public String getViewName() {
        return viewName;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    public Map<String, Object> getViewModel() {
        return viewModel;
    }

    public void addViewModel(String key, Object value) {
        viewModel.put(key, value);
    }
}
