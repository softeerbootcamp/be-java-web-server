package handler;

public class ResourceHandler implements Handler{
    @Override
    public String handle(String url) {
        if (url.equals("/")) {
            return "/index.html";
        }

        return url;
    }
}
