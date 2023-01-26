package bejavawebserver.model;

import lombok.Data;
import lombok.NonNull;

@Data
public class QnaForm {
    @NonNull
    private String writer;
    @NonNull
    private String title;
    private String contents;
}
