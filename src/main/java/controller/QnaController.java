package controller;

import db.DBConnection;
import request.HttpRequest;
import response.HttpResponse;
import view.Model;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;

public class QnaController implements Controller {

    private static final String ENCODE_CHARSET = "UTF-8";

    @Override
    public String doGet(HttpRequest request, HttpResponse response, Model model) {
        //model.addAttribute("users", Database.findAll());
        return "./templates/qna/form.html";
    }

    @Override
    public String doPost(HttpRequest request, HttpResponse response, Model model) {

        try {
            // writer=1234&title=1234&contents=1234
            Map<String, String> formInfo = request.parseBody();
            String writer = formInfo.get("writer");
            writer = URLDecoder.decode(writer, ENCODE_CHARSET);
            String title = formInfo.get("title");
            title = URLDecoder.decode(title, ENCODE_CHARSET);
            String contents = formInfo.get("contents");
            contents = URLDecoder.decode(contents, ENCODE_CHARSET);
            DBConnection.storeInfo(writer, title, contents);
            response.redirect("/index.html");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return "";
    }
}
