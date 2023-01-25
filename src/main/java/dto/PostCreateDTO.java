package dto;

import java.util.Map;

public class PostCreateDTO {
    public static final String WRITER = "writer";
    public static final String TITLE = "title";
    public static final String CONTENT = "contents";

    private String writer;
    private String title;
    private String content;

    private PostCreateDTO(String writer, String title, String content) {
        this.writer = writer;
        this.title = title;
        this.content = content;
    }

    public static PostCreateDTO of(Map<String, String> parameters) {
        return new PostCreateDTO(parameters.get(WRITER), parameters.get(TITLE), parameters.get(CONTENT));
    }

    public String getWriter() {
        return writer;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Boolean isEmpty() {
        return writer.isEmpty() || title.isEmpty() || content.isEmpty();
    }
}

