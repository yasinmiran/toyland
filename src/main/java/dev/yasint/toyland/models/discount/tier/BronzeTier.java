package dev.yasint.toyland.models.discount.tier;

import dev.yasint.toyland.models.discount.Discount;

/**
 * A concrete implementation of the abstract tier class
 */

public class BronzeTier extends TierDiscountDecorator {
    public BronzeTier(Discount tempDiscount) {
        super(tempDiscount);
    }

    @Override
    public double getTotalDiscount() {
        double amount = tempDiscount.getTotalDiscount();
        return amount - (amount * (2.0 / 100.0));
    }
}
