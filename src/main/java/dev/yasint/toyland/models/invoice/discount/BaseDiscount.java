package dev.yasint.toyland.models.invoice.discount;

/**
 * All discounts starts from an instance of
 * this base class. Note the price of the target
 * must be provided first.
 */
public class BaseDiscount implements Discount {

    final double price;

    public BaseDiscount(double price) {
        this.price = price;
    }

    @Override
    public double getTotalDiscount() {
        return price;
    }

}
