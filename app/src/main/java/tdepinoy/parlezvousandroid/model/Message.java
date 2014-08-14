package tdepinoy.parlezvousandroid.model;

public class Message {
    private String author;
    private String content;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String message) {
        this.content = message;
    }

    public String toString() {
        return "Author: " + author + "\n\t" + content + "\n";
    }
}
