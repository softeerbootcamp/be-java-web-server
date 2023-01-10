package handler;

public class ViewHandler implements Handler{
    @Override
    public String handle(String url) {
        if (url.equals("/")) {
            return "/index.html";
        }

        return url;
    }
}
