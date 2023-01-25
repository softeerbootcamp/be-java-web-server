package model;

public class Post {
    User user;
    String title;
    String Content;

    public Post(User user, String title, String content) {
        this.user = user;
        this.title = title;
        Content = content;
    }
}
