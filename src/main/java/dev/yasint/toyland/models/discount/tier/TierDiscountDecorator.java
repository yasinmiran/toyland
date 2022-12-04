package dev.yasint.toyland.models.discount.tier;

import dev.yasint.toyland.models.discount.Discount;

/**
 * This is a abstract class declaration of the various discount tiers
 * using the discount interface
 */

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
