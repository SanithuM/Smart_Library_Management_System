package slms.decorator;

import slms.model.K2559097_BookComponent;

public class K2559097_SpecialEditionBook extends K2559097_BookDecorator {
    public K2559097_SpecialEditionBook(K2559097_BookComponent book) {
        super(book);
    }

    @Override
    public String getDescription() {
        return super.getDescription() + " (Collector's Edition - Hardcover)";
    }
}
