package Controller;

import http.request.HttpMethod;
import http.request.HttpRequest;
import http.response.HttpResponse;
import service.MemoService;

public class MemoController implements Controller {

    public static final String PREFIX = "/memo";
    private final MemoService memoService;

    public MemoController(MemoService memoService) {
        this.memoService = memoService;
    }

    @Override
    public void process(HttpRequest request, HttpResponse response) {
        String url = request.getUrl();
        String path = url.split(PREFIX)[1];

        if (path.equals("/create") && request.getMethod() == HttpMethod.POST) {

            return;
        }
    }


}
