package dev.yasint.toyland.models.discount.tier;

import dev.yasint.toyland.models.discount.Discount;

public class GoldTier extends TierDiscountDecorator {

    public GoldTier(Discount tempDiscount) {
        super(tempDiscount);
    }

    @Override
    public double getTotalDiscount() {
        double amount = tempDiscount.getTotalDiscount();
        return amount - (amount * (8.0 / 100.0));

    }

}
