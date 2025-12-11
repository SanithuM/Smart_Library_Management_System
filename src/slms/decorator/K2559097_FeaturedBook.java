package slms.decorator;

import slms.model.K2559097_BookComponent;

public class K2559097_FeaturedBook extends K2559097_BookDecorator {
    public K2559097_FeaturedBook(K2559097_BookComponent book) {
        super(book);
    }

    @Override
    public String getDescription() {
        return "[FEATURED] " + super.getDescription() + " *** Best Seller ***";
    }
}
