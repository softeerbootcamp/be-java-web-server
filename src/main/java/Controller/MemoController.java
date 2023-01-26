package Controller;

import exception.NullValueException;
import exception.UrlNotFoundException;
import http.request.HttpMethod;
import http.request.HttpRequest;
import http.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.MemoService;
import util.HttpRequestUtils;

import java.util.Map;

public class MemoController implements Controller {

    private static final Logger logger = LoggerFactory.getLogger(MemoController.class);
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
            createMemo(request, response);
            return;
        }

        throw new UrlNotFoundException("잘못된 URL 요청입니다.");
    }

    private void createMemo(HttpRequest request, HttpResponse response) {
        try {
            String body = request.getBody();

            Map<String, String> memoInfo = HttpRequestUtils.parseBodyMessage(body);
            memoService.write(memoInfo);

            response.redirect(request, "/index.html");
        } catch (NullValueException e) {
            logger.error(e.getMessage());
            response.redirect(request, "/memo/form.html");
        }
    }
}
