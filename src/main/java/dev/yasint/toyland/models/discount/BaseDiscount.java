package dev.yasint.toyland.models.discount;

/**
 * All discounts start from an instance of this base class.
 * NB: the price of the target must be provided first
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
