package ru.otus.lesson14hw.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Collection;

@Document
public class Book {
    @Id
    private String id;
    private String name;
    private String description;
    private String content;
    private Genre genre;
    @DBRef
    private Collection<Author> authors = new ArrayList<>();
    @DBRef
    private Collection<Comment> comments = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public void setAuthors(Collection<Author> authors) {
        this.authors.addAll(authors);
    }

    public void setComments(Collection<Comment> comments) {
        this.comments.addAll(comments);
    }

    public void setAuthor(Author author) {
        this.authors.add(author);
    }

    public void setComment(Comment comment) {
        this.comments.add(comment);
    }

    public Collection<Author> getAuthors() {
        return authors;
    }

    public Collection<Comment> getComments() {
        return comments;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", content='" + content + '\'' +
                ", genre=" + genre +
                ", authors=" + authors +
                ", comments=" + comments +
                '}';
    }
}