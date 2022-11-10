package dev.yasint.toyland.models.invoice.discount.promotions;

import dev.yasint.toyland.models.invoice.discount.Discount;

public abstract class PromotionsDiscountDecorator implements Discount {

    final Discount tempDiscount;

    public PromotionsDiscountDecorator(Discount tempDiscount) {
        this.tempDiscount = tempDiscount;
    }

    @Override
    public double getTotalDiscount() {
        return tempDiscount.getTotalDiscount();
    }

}
