package dev.yasint.toyland.controllers;

import dev.yasint.toyland.models.discount.BaseDiscount;
import dev.yasint.toyland.models.discount.Discount;
import dev.yasint.toyland.models.discount.promotions.ChristmasPromotion;
import dev.yasint.toyland.models.discount.tier.SilverTier;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class CartControllerImplTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void isDiscountIsApplied() {
        Discount discount = new ChristmasPromotion(
                new SilverTier(
                        new BaseDiscount(100)
                )
        );
        assertEquals(discount.getTotalDiscount(), 47.5);
    }

    @Test
    void isDiscountCalculedFailed() {
        Discount discount = new ChristmasPromotion(
                new SilverTier(
                        new BaseDiscount(100)
                )
        );
        assertNotEquals(discount.getTotalDiscount(), 40);
    }

}