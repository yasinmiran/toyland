package dev.yasint.toyland.models.discount.promotions;

import dev.yasint.toyland.models.discount.Discount;

public class ChristmasPromotion extends PromotionsDiscountDecorator {

    public ChristmasPromotion(Discount tempDiscount) {
        super(tempDiscount);
    }

    @Override
    public double getTotalDiscount() {
        return super.getTotalDiscount() - (super.getTotalDiscount() * .5);
    }

}
