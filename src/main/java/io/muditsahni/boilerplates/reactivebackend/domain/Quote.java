package io.muditsahni.boilerplates.reactivebackend.domain;

import java.util.Objects;

public class Quote {

    private String id;
    private String book;
    private String content;

    public Quote() {}

    public Quote(String id, String book, String content) {
        this.id = id;
        this.book = book;
        this.content = content;
    }

    public String getId() {
        return this.id;
    }

    public String getBook() {
        return this.book;
    }

    public String getContent() {
        return this.content;
    }

    @Override
    public String toString() {
        return "Quote{" +
                "id='" + id + '\'' +
                ", book='" + book + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Quote quote = (Quote) o;

        if (!Objects.equals(id, quote.id)) return false;
        if (!Objects.equals(book, quote.book)) return false;
        return Objects.equals(content, quote.content);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (book != null ? book.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        return result;

    }
}
