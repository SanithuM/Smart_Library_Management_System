package slms.builder;

import slms.model.K2559097_Book;

public class K2559097_BookBuilder {
    private K2559097_Book book;

    public K2559097_BookBuilder(String id, String title, String isbn) {
        // Start with the mandatory fields
        this.book = new K2559097_Book(id, title, isbn);
    }

    public K2559097_BookBuilder setAuthor(String author) {
        book.setAuthor(author);
        return this; // Return builder for chaining
    }

    public K2559097_BookBuilder setCategory(String category) {
        book.setCategory(category);
        return this;
    }

    public K2559097_BookBuilder setEdition(String edition) {
        book.setEdition(edition);
        return this;
    }

    public K2559097_BookBuilder addTag(String tag) {
        book.addTag(tag);
        return this;
    }

    public K2559097_Book build() {
        return book;
    }
}
