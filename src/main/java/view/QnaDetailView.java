package view;

import enums.ContentType;
import enums.HttpStatus;
import request.HttpRequest;
import response.HttpResponse;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

public class QnaDetailView implements View{
    @Override
    public void render(HttpRequest request, HttpResponse response, Model data) throws IOException, URISyntaxException {
        response.setStatus(HttpStatus.OK);
        response.setContentType(ContentType.HTML);
        byte[] body = FileIoUtils.loadFileFromClasspath("./templates/qna/show.html");
        Map<String,String> Details = (Map<String, String>) data.getAttribute("details");
        body = showDetailById(body,Details).getBytes();
        response.addToHeader("Content-Length", String.valueOf(body.length));
        response.setBody(body);//body에는 요청한 파일 내용이 들어감
    }

    private static String showDetailById(byte[] bytes,Map<String,String> Details){
        StringBuilder content = new StringBuilder();
        content.append("<div class=\"container\" id=\"main\">\n" +
                "    <div class=\"col-md-12 col-sm-12 col-lg-12\">\n" +
                "        <div class=\"panel panel-default\">\n" +
                "          <header class=\"qna-header\">\n" +
                "              <h2 class=\"qna-title\">"+Details.get("title")+"</h2>\n" +
                "          </header>\n" +
                "          <div class=\"content-main\">\n" +
                "              <article class=\"article\">\n" +
                "                  <div class=\"article-header\">\n" +
                "                      <div class=\"article-header-thumb\">\n" +
                "                          <img src=\"https://graph.facebook.com/v2.3/100000059371774/picture\" class=\"article-author-thumb\" alt=\"\">\n" +
                "                      </div>\n" +
                "                      <div class=\"article-header-text\">\n" +
                "                          <a href=\"/users/92/kimmunsu\" class=\"article-author-name\">"+Details.get("writer")+"</a>\n" +
                "                          <a href=\"/questions/413\" class=\"article-header-time\" title=\"퍼머링크\">\n" +
                "                              "+Details.get("time")+"\n" +
                "                              <i class=\"icon-link\"></i>\n" +
                "                          </a>\n" +
                "                      </div>\n" +
                "                  </div>\n" +
                "                  <div class=\"article-doc\">\n" +
                "                      <p>"+Details.get("contents")+"</p>\n" +
                "                  </div>\n" +
                "                  <div class=\"article-utils\">\n" +
                "                  </div>\n" +
                "              </article>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</div>");

        String html = new String(bytes);
        html = html.replace("<!--<div class=\"container\" id=\"main\">-->\n" +
                "<!--    <div class=\"col-md-12 col-sm-12 col-lg-12\">-->\n" +
                "<!--        <div class=\"panel panel-default\">-->\n" +
                "<!--          <header class=\"qna-header\">-->\n" +
                "<!--              <h2 class=\"qna-title\">InitializingBean implements afterPropertiesSet() 호출되지 않는 문제.</h2>-->\n" +
                "<!--          </header>-->\n" +
                "<!--          <div class=\"content-main\">-->\n" +
                "<!--              <article class=\"article\">-->\n" +
                "<!--                  <div class=\"article-header\">-->\n" +
                "<!--                      <div class=\"article-header-thumb\">-->\n" +
                "<!--                          <img src=\"https://graph.facebook.com/v2.3/100000059371774/picture\" class=\"article-author-thumb\" alt=\"\">-->\n" +
                "<!--                      </div>-->\n" +
                "<!--                      <div class=\"article-header-text\">-->\n" +
                "<!--                          <a href=\"/users/92/kimmunsu\" class=\"article-author-name\">kimmunsu</a>-->\n" +
                "<!--                          <a href=\"/questions/413\" class=\"article-header-time\" title=\"퍼머링크\">-->\n" +
                "<!--                              2015-12-30 01:47-->\n" +
                "<!--                              <i class=\"icon-link\"></i>-->\n" +
                "<!--                          </a>-->\n" +
                "<!--                      </div>-->\n" +
                "<!--                  </div>-->\n" +
                "<!--                  <div class=\"article-doc\">-->\n" +
                "<!--                      <p>A 에 의존성을 가지는 B라는 클래스가 있습니다.</p><p>B라는 클래스는 InitializingBean 을 상속하고 afterPropertiesSet을 구현하고 있습니다.-->\n" +
                "<!--                      서버가 가동되면서 bean들이 초기화되는 시점에 B라는 클래스의 afterPropertiesSet 메소드는</p><p>A라는 클래스의 특정 메소드인 afunc()를 호출하고 있습니다.</p>-->\n" +
                "<!--                  </div>-->\n" +
                "<!--                  <div class=\"article-utils\">-->\n" +
                "<!--                  </div>-->\n" +
                "<!--              </article>-->\n" +
                "<!--        </div>-->\n" +
                "<!--    </div>-->\n" +
                "<!--</div>-->",content);
        return html;
    }
}
