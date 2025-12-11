package slms.decorator;

import slms.model.K2559097_BookComponent;

public abstract class K2559097_BookDecorator implements K2559097_BookComponent {
    protected K2559097_BookComponent tempBook; // The object being decorated

    public K2559097_BookDecorator(K2559097_BookComponent book) {
        this.tempBook = book;
    }

    @Override
    public String getDescription() {
        return tempBook.getDescription();
    }
}
