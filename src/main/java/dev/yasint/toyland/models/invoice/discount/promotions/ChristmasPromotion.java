package dev.yasint.toyland.models.invoice.discount.promotions;

import dev.yasint.toyland.models.invoice.discount.Discount;

public class ChristmasPromotion extends PromotionsDiscountDecorator {

    public ChristmasPromotion(Discount tempDiscount) {
        super(tempDiscount);
    }

    @Override
    public double getTotalDiscount() {
        return super.getTotalDiscount() - (super.getTotalDiscount() * .5);
    }

}
