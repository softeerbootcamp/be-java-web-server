package bejavawebserver.model;

import lombok.Data;
import lombok.NonNull;

@Data
public class Qna {
    private String writer;
    private String title;
    private String contents;
    private String time;

    public Qna(QnaForm qnaForm, String time) {
        this.writer = qnaForm.getWriter();
        this.title = qnaForm.getTitle();
        this.contents = qnaForm.getContents();
        this.time = time;
    }

    public Qna(String writer, String title, String contents, String time) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.time = time;
    }
}
