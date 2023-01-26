package bejavawebserver.model;

import lombok.Data;

@Data
public class Qna {
    private String writer;
    private String contents;
    private String time;

    public Qna(QnaForm qnaForm, String time) {
        this.writer = qnaForm.getWriter();
        this.contents = qnaForm.getContents();
        this.time = time;
    }

    public Qna(String writer, String contents, String time) {
        this.writer = writer;
        this.contents = contents;
        this.time = time;
    }
}
