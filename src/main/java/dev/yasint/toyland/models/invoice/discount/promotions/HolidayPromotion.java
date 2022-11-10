package dev.yasint.toyland.models.invoice.discount.promotions;

import dev.yasint.toyland.models.invoice.discount.Discount;

public class HolidayPromotion extends PromotionsDiscountDecorator {

    public HolidayPromotion(Discount discount) {
        super(discount);
    }

    @Override
    public double getTotalDiscount() {
        return super.getTotalDiscount() - (super.getTotalDiscount() * .25);
    }

}
