package dev.yasint.toyland.models.discount.tier;

import dev.yasint.toyland.models.discount.Discount;

public abstract class TierDiscountDecorator implements Discount {

    final Discount tempDiscount;

    public TierDiscountDecorator(Discount tempDiscount) {
        this.tempDiscount = tempDiscount;
    }

    @Override
    public double getTotalDiscount() {
        return this.tempDiscount.getTotalDiscount();
    }

}
