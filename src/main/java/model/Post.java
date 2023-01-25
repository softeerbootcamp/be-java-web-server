package model;

public class Post {
    private String author;
    private String title;
    private String contents;

    public Post (String initAuthor, String initTitle, String initContent){
        author = initAuthor;
        title = initTitle;
        contents = initContent;
    }

    public String getAuthor(){
        return author;
    }
}
