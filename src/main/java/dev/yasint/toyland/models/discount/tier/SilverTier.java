package dev.yasint.toyland.models.discount.tier;

import dev.yasint.toyland.models.discount.Discount;

public class SilverTier extends TierDiscountDecorator {

    public SilverTier(Discount tempDiscount) {
        super(tempDiscount);
    }

    @Override
    public double getTotalDiscount() {
        double amount = tempDiscount.getTotalDiscount();
        return amount - (amount * (5.0 / 100.0));

    }
}
