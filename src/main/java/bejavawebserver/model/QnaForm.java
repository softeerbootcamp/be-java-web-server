package bejavawebserver.model;

import lombok.Data;
import lombok.NonNull;

@Data
public class QnaForm {
    @NonNull
    private String writer;
    @NonNull
    private String contents;

    public QnaForm(@NonNull String writer, @NonNull String contents) {
        this.writer = writer;
        this.contents = contents;
    }
}
