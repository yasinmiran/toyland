package dev.yasint.toyland.controllers;

import dev.yasint.toyland.ToylandApplication;
import dev.yasint.toyland.configs.H2JpaConfiguration;
import dev.yasint.toyland.models.discount.BaseDiscount;
import dev.yasint.toyland.models.discount.Discount;
import dev.yasint.toyland.models.discount.promotions.ChristmasPromotion;
import dev.yasint.toyland.models.discount.tier.SilverTier;
import dev.yasint.toyland.repositories.CartRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ToylandApplication.class, H2JpaConfiguration.class})
@ActiveProfiles("test")
public class CartControllerImplTest {

    @Autowired
    private CartRepository cartRepository;


    @BeforeEach
    void setUp() {




    }

    @AfterEach
    void tearDown() { }

    @Test
    void isDiscountApplied() {
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