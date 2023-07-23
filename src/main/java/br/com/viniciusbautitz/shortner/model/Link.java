package br.com.viniciusbautitz.shortner.model;

public class Link {

    private String _id;
    private String name;
    private String link;
    private String author;
    private String origin;
    private String createdAt;

    public Link() {
    }

    public Link(String name, String link, String origin, String author) {
        this.name = name;
        this.origin = origin;
        this.author = author;

        setLink(link);
    }

    public String getId() {
        return _id;
    }

    public void setId(String id) {
        this._id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        if (!link.startsWith("https://") && !link.startsWith("http://")) {
            this.link = "http://" + link;
        } else this.link = link;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Link{" +
                "_id='" + _id + '\'' +
                ", name='" + name + '\'' +
                ", link='" + link + '\'' +
                ", author='" + author + '\'' +
                ", origin='" + origin + '\'' +
                ", createdAt='" + createdAt + '\'' +
                '}';
    }

}
